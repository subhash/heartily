(ns heartily.server
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [heartily.views :as views]
            [heartily.google-oauth :refer :all]
            [ring.util.response :as resp]
            [clj-oauth2.client :as oauth2]))

(defn initialize [{:keys [params session]}]
  (let [access-token (google-access-token params)
        email (get-user-email access-token)
        session (merge session {:access-token access-token :email email})]
    (when-not
      (get-hr-datastream access-token)
      (create-hr-datastream access-token))
    (-> (resp/redirect "/" )
        (assoc :session session))))

(defn foo [{{access-token :access-token} :session}]
  (remove-hr-datastream access-token)
  (resp/redirect "/"))

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
  (GET "/foo" [] foo)
  (GET "/fit" {{:keys [access-token]} :session {url :url} :params}
       (get-fit-url access-token url))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))



