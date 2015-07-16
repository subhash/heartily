(ns heartily.strava
  (:require [clj-oauth2.client :as oauth2]
            [cheshire.core :refer [parse-string generate-string]]
            [clj-http.client :as http]
            [clojure.walk :refer [keywordize-keys]]
            [heartily.google-oauth :refer :all]))

(def activity-mapping
  { "Ride" 1
    "Run"  8
    "Swim" 82
    "Hike" 35
    "Walk" 7
    "StandUpPaddling" 79
    "Yoga" 100})

(def strava-oauth2
  {:authorization-uri "https://www.strava.com/oauth/authorize"
   :access-token-uri "https://www.strava.com/oauth/token"
   :client-id "7191"
   :client-secret "104d50c144763dfc205d1f1f6a6fb60e6bd0da07"
   :access-query-param :access_token
   :grant-type "authorization_code"
   :scope ["view_private"]
   :token_type "bearer"
   :redirect-uri "https://floating-hamlet-5403.herokuapp.com/strava_callback"
   ;:redirect-uri "http://localhost:3000/strava_callback"
   :response-type "code"})

(def auth-request (oauth2/make-auth-request strava-oauth2))

(defn access-token [params]
  (oauth2/get-access-token strava-oauth2 params auth-request))

(defn oauth2-get
  ([url access-token]
   (oauth2-get url access-token {}))
  ([url access-token query-params]
   (let [request {:headers {"Authorization" (str "Bearer " access-token)}
                  :query-params query-params}
         response (http/get (str "https://www.strava.com/api/v3" url) request)]
     (-> (:body response) parse-string) )))

(defn activity-stream [token activity]
  (let [streams (oauth2-get
                  (str "/activities/" (:id activity) "/streams/time,distance,heartrate")
                  token
                  {"resolution" "low"})]
    (assoc activity :streams
      (->> streams
           (map #(vector (% "type") (% "data")))
           (into {})
           keywordize-keys))))


(defn activities [token]
  (let [data (oauth2-get "/athlete/activities" token)
        sanitized (map keywordize-keys data)
        snipped (map #(select-keys % [:id :start_date_local :name :description :type :elapsed_time]) sanitized)]
    (map (partial activity-stream token) snipped)))


(defn activity->datapoint [activity]
  (let [starttime (* 1000000 (-> (:start_date_local activity) clojure.instant/read-instant-date .getTime))
        endtime  (+ starttime (* 1000000000 (:elapsed_time activity)))
        act-datapoint {:dataSourceId (datastream-id activity-datatype-id)
                       :maxEndTimeNs endtime
                       :minStartTimeNs starttime
                       :point [{:dataTypeName activity-datatype-id
                                :endTimeNanos endtime
                                :originDataSourceId ""
                                :startTimeNanos starttime
                                :value [{:intVal (get activity-mapping (:type activity) 108)}]}]}
        hr-time (map vector
                     (get-in activity [:streams :heartrate])
                     (get-in activity [:streams :time]))
        hr-points (for [[hr htime] hr-time]
                    {:dataTypeName hr-datatype-id
                     :endTimeNanos (+ starttime (* 1000000000 htime))
                     :originDataSourceId ""
                     :startTimeNanos (+ starttime (* 1000000000 htime))
                     :value [{:fpVal hr}]})
        hr-datapoints {:dataSourceId (datastream-id hr-datatype-id)
                       :maxEndTimeNs endtime
                       :minStartTimeNs starttime
                       :point hr-points}
        ] [act-datapoint hr-datapoints]))


