(ns heartily.server
  (:use compojure.core)
  (:require [clj-oauth2.client :as oauth2]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [heartily.views :as views]))

;(def auth-url "https://accounts.google.com/o/oauth2/auth?redirect_uri=http://localhost:3000/code&response_type=code&client_id=776188546157-5btudf4ic02tk5vsh6tdp72dpoi54b3v.apps.googleusercontent.com&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Ffitness.activity.write&approval_prompt=force&access_type=offline")


(defn auth-req []
  (let [login-uri "https://accounts.google.com"
        google-com-oauth2
        {:authorization-uri (str login-uri "/o/oauth2/auth")
         :access-token-uri (str login-uri "/o/oauth2/token")
         :redirect-uri "http://localhost:3000/code"
         :client-id "776188546157-5btudf4ic02tk5vsh6tdp72dpoi54b3v.apps.googleusercontent.com"
         :client-secret "Fhf-_ms1QH43MOH1CkZcxvt3"
         :access-query-param :access_token
         :scope ["https://www.googleapis.com/auth/fitness.activity.write"]
         :grant-type "authorization_code"
         :access-type "offline"
         :approval_prompt "force"}]
    (oauth2/make-auth-request google-com-oauth2)))


(defroutes app-routes
  (GET "/" [] (views/index-page (:uri (auth-req))))
  (GET "/code" {params :params} (str params))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))



