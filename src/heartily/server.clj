(ns heartily.server
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [heartily.views :as views]
            [heartily.google-oauth :refer :all]
            [ring.util.response :as resp]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.multipart-params :as mp]
            [clj-oauth2.client :as oauth2]
            [heartily.gpx :as gpx]
            [cheshire.core :refer [parse-string generate-string]]))

(defn initialize [{:keys [params session]}]
  (let [access-token (google-access-token params)
        email (get-user-email access-token)
        session (merge session {:access-token access-token :email email})]
    (when-not
      (get-datastream access-token hr-datastream-id)
      (create-hr-datastream access-token))
    (when-not
      (get-datastream access-token (datastream-id activity-datatype-id))
      (create-activity-datastream access-token))
    (-> (resp/redirect "/" )
        (assoc :session session))))

(defn load-data [{{access-token :access-token} :session
                  {{tempfile :tempfile} :file} :params} ]
  (let [trkpts (-> (gpx/gpx->map "" tempfile)
                   (get-in [:activity/track :track/track-points]))
        points (for [t trkpts :when (:track-point/heart-rate t)]
                 {:dataTypeName hr-datatype-id
                  :endTimeNanos (* 1000000 (.getTime (:track-point/time t)))
                  :originDataSourceId ""
                  :startTimeNanos (* 1000000 (.getTime (:track-point/time t)))
                  :value [{:fpVal (float (:track-point/heart-rate t))}]})]
    (when (seq points)
      (let [[mn mx] ((juxt (partial apply min) (partial apply max)) (map :startTimeNanos points))
            hr-datapoints {:dataSourceId hr-datastream-id
                           :maxEndTimeNs mx
                           :minStartTimeNs mn
                           :point points}
            act-datapoint {:dataSourceId (datastream-id activity-datatype-id)
                           :maxEndTimeNs mx
                           :minStartTimeNs mn
                           :point [{:dataTypeName activity-datatype-id
                                    :endTimeNanos mx
                                    :originDataSourceId ""
                                    :startTimeNanos mn
                                    :value [{:intVal 108}]}]}
            dataset-id (str mn "-" mx)]
        (create-dataset access-token hr-datastream-id dataset-id hr-datapoints)
        (create-dataset access-token (datastream-id activity-datatype-id) dataset-id act-datapoint)
        (println "dataset-id " dataset-id)))
    (resp/redirect "/")))


(defn logout [{session :session}]
  (-> (resp/redirect "/")
      (assoc :session (dissoc session :access-token) )))


(defroutes app-routes
  (GET "/" {{token :access-token email :email} :session}
       (if token
         (views/index-page (list-datapoints token (datastream-id activity-datatype-id) "*") email)
         (views/login-page (:uri auth-req))))
  (GET "/oauth2_callback" [] initialize)
  (GET "/logout" [] logout)
  (GET "/load-data" [] load-data)
  (GET "/token" {{access-token :access-token} :session} (pr-str access-token))
  (GET "/fit" {{:keys [access-token]} :session {:keys [url method] :or {url "/dataSources" method :get}} :params}
       (get-fit-url access-token url (keyword method)))
  (mp/wrap-multipart-params
   (POST "/upload" [] load-data))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(defn -main []
  (jetty/run-jetty app {:port 80 :join? false}))


