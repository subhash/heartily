(ns heartily.google-oauth
  (:require [clj-oauth2.client :as oauth2]
            [cheshire.core :refer [parse-string generate-string]]
            [clojure.pprint :refer [pprint]]
            [clj-http.client :as http]))

(def login-uri "https://accounts.google.com")
(def fit-uri "https://www.googleapis.com/fitness/v1/users/me")
(def user-uri "https://www.googleapis.com/oauth2/v1/userinfo")
(def datasource-uri (str fit-uri "/dataSources"))
(def hr-datastream-id "raw:com.google.heart_rate.bpm:776188546157:")
(def hr-datatype-id "com.google.heart_rate.bpm")

(def google-com-oauth2
        {:authorization-uri (str login-uri "/o/oauth2/auth")
         :access-token-uri (str login-uri "/o/oauth2/token")
         :redirect-uri "http://localhost:3000/oauth2_callback"
         :client-id "776188546157-5btudf4ic02tk5vsh6tdp72dpoi54b3v.apps.googleusercontent.com"
         :client-secret "Fhf-_ms1QH43MOH1CkZcxvt3"
         :access-query-parvam :access_token
         :scope ["https://www.googleapis.com/auth/fitness.activity.write"
                 "https://www.googleapis.com/auth/fitness.body.write"
                 "https://www.googleapis.com/auth/fitness.location.write"
                 "https://www.googleapis.com/auth/userinfo.email"]
         :grant-type "authorization_code"
         :access-type "offline"
         :approval_prompt "force"})

(def auth-req (oauth2/make-auth-request google-com-oauth2))

(defn google-access-token [params]
  (oauth2/get-access-token google-com-oauth2 params auth-req))

(defn get-fit-url [access-token url]
  (let [response (oauth2/get (str fit-uri url) {:oauth2 access-token})]
    (str "<pre>" (with-out-str (pprint (parse-string (:body response)))) "</pre>")))

(defn get-user-email [access-token]
  (let [response (oauth2/get user-uri {:oauth2 access-token})]
    (-> (:body response) parse-string (get "email"))))

(defn list-data-sources [access-token]
  (let [response (oauth2/get datasource-uri {:oauth2 access-token})]
    (get (parse-string (:body response)) "dataSource")))

(defn get-hr-datastream [access-token]
  (some #(when (= hr-datastream-id (% "dataStreamId")) %)
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

(defn create-hr-dataset [access-token dataset-id datapoints]
  ((oauth2/wrap-oauth2 http/request)
   {:url (str datasource-uri "/" hr-datastream-id "/datasets/" dataset-id)
    :method :patch
    :oauth2 access-token
    :body (generate-string datapoints)
    :headers {"Content-Type" "application/json" "encoding" "utf-8"}}))

(defn remove-hr-datastream [access-token]
  (oauth2/delete
   (str datasource-uri "/" hr-datastream-id)
   {:oauth2 access-token}))



