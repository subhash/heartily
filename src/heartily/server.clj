(ns heartily.server
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [heartily.views :as views]
            [heartily.google-oauth :refer :all]
            [ring.util.response :as resp]))

(defn save-token [req]
  (let [foo (println "about to access-token")
        access-token (google-access-token (:params req))
        data (google-get "https://www.googleapis.com/fitness/v1/users/me/dataSources" access-token)]
    (println "data - " data)
    (resp/redirect "/done" )))

(defroutes app-routes
  (GET "/" [] (views/index-page (google-auth-uri)))
  (GET "/oauth2_callback" [] save-token)
  (GET "/done" [] "done")
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))



