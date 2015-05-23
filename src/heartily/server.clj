(ns heartily.server
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [heartily.views :as views]
            [heartily.google-oauth :refer :all]
            [ring.util.response :as resp]
            [clj-oauth2.client :as oauth2]
            [heartily.gpx :as gpx]
            [cheshire.core :refer [parse-string generate-string]]))

(defn initialize [{:keys [params session]}]
  (let [access-token (google-access-token params)
        email (get-user-email access-token)
        session (merge session {:access-token access-token :email email})]
    (when-not
      (get-hr-datastream access-token)
      (create-hr-datastream access-token))
    (-> (resp/redirect "/" )
        (assoc :session session))))

(defn load-data [{{access-token :access-token} :session}]
  (let [trkpts (-> (gpx/gpx->map "" (java.io.File. "resources/data/trek1.gpx"))
                   (get-in [:activity/track :track/track-points]))
        points (for [t trkpts]
                 {:dataTypeName hr-datatype-id
                  :endTimeNanos (* 1000000 (.getTime (:track-point/time t)))
                  :originDataSourceId ""
                  :startTimeNanos (* 1000000 (.getTime (:track-point/time t)))
                  :value [{:fpVal (float (:track-point/heart-rate t))}]})
        [mn mx] ((juxt (partial apply min) (partial apply max)) (map :startTimeNanos points))
        datapoints {:dataSourceId hr-datastream-id
                    :maxEndTimeNs mx
                    :minStartTimeNs mn
                    :point points}
        dataset-id (str mn "-" mx)]
    (create-hr-dataset access-token dataset-id datapoints)
    (resp/redirect "/")))

(defn logout [{session :session}]
  (-> (resp/redirect "/")
      (assoc :session (dissoc session :access-token) )))


(defroutes app-routes
  (GET "/" {{token :access-token email :email} :session}
       (if token
         (views/index-page (get-hr-datastream token) email)
         (views/login-page (:uri auth-req))))
  (GET "/oauth2_callback" [] initialize)
  (GET "/logout" [] logout)
  (GET "/load-data" [] load-data)
  (GET "/token" {{access-token :access-token} :session} (pr-str access-token))
  (GET "/fit" {{:keys [access-token]} :session {url :url} :params}
       (get-fit-url access-token url))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))



