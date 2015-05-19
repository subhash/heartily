(ns heartily.server
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [heartily.views :as views]
            [heartily.google-oauth :refer :all]
            [ring.util.response :as resp]))

(defn save-token [{:keys [params session]}]
  (let [access-token (google-access-token params)
        session (assoc session :access-token access-token)
        data (google-get "https://www.googleapis.com/fitness/v1/users/me/dataSources" access-token)]
    (-> (resp/redirect "/" )
        (assoc :session session))))

(defroutes app-routes
  (GET "/" {{token :access-token} :session}
       (if token
         (views/index-page token)
         (views/login-page (google-auth-uri))))
  (GET "/oauth2_callback" [] save-token)
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))



