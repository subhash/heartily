(ns heartily.google-oauth
  (:require [clj-oauth2.client :as oauth2]
            [cheshire.core :refer [parse-string generate-string]]
            [clojure.pprint :refer [pprint]]
            [clj-http.client :as http]))


(defn datastream-id [datatype-id]
  (str "raw:" datatype-id ":776188546157:"))

(def login-uri "https://accounts.google.com")
(def fit-uri "https://www.googleapis.com/fitness/v1/users/me")
(def user-uri "https://www.googleapis.com/oauth2/v1/userinfo")
(def datasource-uri (str fit-uri "/dataSources"))
(def hr-datatype-id "com.google.heart_rate.bpm")
(def hr-datastream-id (datastream-id hr-datatype-id))
(def activity-datatype-id "com.google.activity.segment")


(def google-com-oauth2
        {:authorization-uri (str login-uri "/o/oauth2/auth")
         :access-token-uri (str login-uri "/o/oauth2/token")
         ;:redirect-uri "https://floating-hamlet-5403.herokuapp.com/oauth2_callback"
         :redirect-uri "http://localhost:3000/oauth2_callback"
         :client-id "776188546157-5btudf4ic02tk5vsh6tdp72dpoi54b3v.apps.googleusercontent.com"
         :client-secret "Fhf-_ms1QH43MOH1CkZcxvt3"
         :access-query-param :access_token
         :scope ["https://www.googleapis.com/auth/fitness.activity.write"
                 "https://www.googleapis.com/auth/fitness.body.write"
                 "https://www.googleapis.com/auth/fitness.location.write"
                 "https://www.googleapis.com/auth/userinfo.email"]
         :grant-type "authorization_code"
         :access-type "offline"
         :approval_prompt "force"})

(def strava-oauth2
  {:authorization-uri "https://www.strava.com/oauth/authorize"
   :access-token-uri "https://www.strava.com/oauth/token"
   :client-id "7191"
   :client-secret "104d50c144763dfc205d1f1f6a6fb60e6bd0da07"

   :access-query-param :access_token
   :grant-type "authorization_code"

   :scope ["view_private"]


   ;:redirect-uri "https://floating-hamlet-5403.herokuapp.com/strava_callback"
   :redirect-uri "http://localhost:3000/strava_callback"
   :response-type "code"})

(def auth-req (oauth2/make-auth-request google-com-oauth2))

(defn google-access-token [params]
  (oauth2/get-access-token google-com-oauth2 params auth-req))

(defn strava-access-token [params]
  (oauth2/get-access-token strava-oauth2 params (oauth2/make-auth-request strava-oauth2)))

(defn get-fit-url [access-token url method]
  (let [response
        ((oauth2/wrap-oauth2 http/request)
         {:url (str fit-uri url)
          :oauth2 access-token
          :method method})]
    (str "<pre>" (with-out-str (pprint (parse-string (:body response)))) "</pre>")))

(defn get-strava-activities [access-token]
  (let [response (http/request
                  {:method :get
                   :url "https://www.strava.com/api/v3/athlete/activities"
                   :headers {"Authorization" (str "Bearer " (:access-token access-token))}})]
    (-> (:body response) parse-string )))

(defn get-strava-hr-streams [access-token aid]
  (let [response (http/request
                  {:method :get
                   :url (str "https://www.strava.com/api/v3/activities/" aid "/streams/time,distance,hearrate")
                   :headers {"Authorization" (str "Bearer " (:access-token access-token))}})]
    (-> (:body response) parse-string )))

(defn get-user-email [access-token]
  (let [response (oauth2/get user-uri {:oauth2 access-token})]
    (-> (:body response) parse-string (get "email"))))

(defn list-data-sources [access-token]
  (let [response (oauth2/get datasource-uri {:oauth2 access-token})]
    (get (parse-string (:body response)) "dataSource")))

(defn list-datapoints [access-token datastream-id timerange]
  (let [response (oauth2/get (str datasource-uri "/" datastream-id "/datasets/" timerange) {:oauth2 access-token})]
    (get (parse-string (:body response)) "point")))


(defn get-datastream [access-token datastream-id]
  (some #(when (= datastream-id (% "dataStreamId")) %)
        (list-data-sources access-token)))

(defn create-hr-datastream [access-token]
  (let [body {:dataStreamId hr-datastream-id
              :dataStreamName ""
              :name "Heartily Import"
              :type "raw"
              :application {:name "Heartily"}
              :dataType {:name hr-datatype-id :field [{:name "bpm" :format "floatPoint"}]}}]
    (oauth2/post datasource-uri {:oauth2 access-token
                      :body (generate-string body)
                      :headers {"Content-Type" "application/json" "encoding" "utf-8"}})))

(defn create-activity-datastream [access-token]
  (let [body {:dataStreamId (datastream-id activity-datatype-id)
              :dataStreamName ""
              :name "Heartily Import"
              :type "raw"
              :application {:name "Heartily"}
              :dataType {:name activity-datatype-id
                         :field [{:name "activity" :format "integer"}]}}]
    (oauth2/post datasource-uri {:oauth2 access-token
                      :body (generate-string body)
                      :headers {"Content-Type" "application/json" "encoding" "utf-8"}})))


(defn create-dataset [access-token datastream-id dataset-id datapoints]
  ((oauth2/wrap-oauth2 http/request)
   {:url (str datasource-uri "/" datastream-id "/datasets/" dataset-id)
    :method :patch
    :oauth2 access-token
    :body (generate-string datapoints)
    :headers {"Content-Type" "application/json" "encoding" "utf-8"}}))

(defn remove-hr-datastream [access-token]
  (oauth2/delete
   (str datasource-uri "/" hr-datastream-id)
   {:oauth2 access-token}))



