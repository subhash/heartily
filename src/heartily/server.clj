(ns heartily.server
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [heartily.views :as views]
            [heartily.google-oauth :refer :all]
            [ring.util.response :as resp]
            [clj-oauth2.client :as oauth2]
            [cheshire.core :refer [parse-string generate-string]]
            [clojure.pprint :refer [pprint]]))

(defn create-data-source [access-token]
  (let [uri "https://www.googleapis.com/fitness/v1/users/me/dataSources"
        body {:dataStreamId "raw:com.google.heart_rate.bpm:776188546157:"
              :dataStreamName ""
              :name "Heartily Import"
              :type "raw"
              :application {:name "Heartily"}
              :dataType {:name "com.google.heart_rate.bpm" :field [{:name "bpm" :format "floatPoint"}]}}]
    (oauth2/post uri {:oauth2 access-token
                      :body (generate-string body)
                      :headers {"Content-Type" "application/json" "encoding" "utf-8"}})))

(defn save-token [{:keys [params session]}]
  (let [access-token (google-access-token params)
        session (assoc session :access-token access-token)]
    ;(create-data-source access-token)
    (-> (resp/redirect "/dataSources" )
        (assoc :session session))))

(defn logout [{session :session}]
  (-> (resp/redirect "/")
      (assoc :session (dissoc session :access-token) )))

(defn get-data-sources [{{:keys [access-token]} :session}]
  (let [response (google-get "https://www.googleapis.com/fitness/v1/users/me/dataSources" access-token)]
    (str "<pre>" (with-out-str (pprint (parse-string (:body response)))) "</pre>")))

(defroutes app-routes
  (GET "/" {{token :access-token} :session}
       (if token
         (views/index-page token)
         (views/login-page (google-auth-uri))))
  (GET "/oauth2_callback" [] save-token)
  (GET "/logout" [] logout)
  (GET "/dataSources" [] get-data-sources)
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))



