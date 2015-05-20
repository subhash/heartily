(ns heartily.google-oauth
  (:require [clj-oauth2.client :as oauth2]))

(def login-uri "https://accounts.google.com")
(def google-com-oauth2
        {:authorization-uri (str login-uri "/o/oauth2/auth")
         :access-token-uri (str login-uri "/o/oauth2/token")
         :redirect-uri "http://localhost:3000/oauth2_callback"
         :client-id "776188546157-5btudf4ic02tk5vsh6tdp72dpoi54b3v.apps.googleusercontent.com"
         :client-secret "Fhf-_ms1QH43MOH1CkZcxvt3"
         :access-query-param :access_token
         :scope ["https://www.googleapis.com/auth/fitness.activity.write"
                 "https://www.googleapis.com/auth/fitness.body.write"
                 "https://www.googleapis.com/auth/fitness.location.write"]
         :grant-type "authorization_code"
         :access-type "offline"
         :approval_prompt "force"})

(def auth-req (oauth2/make-auth-request google-com-oauth2))

(defn google-auth-uri []
  (:uri auth-req))

(defn google-get [uri access-token]
  (let [response (oauth2/get uri {:oauth2 access-token})]
    response))

(defn google-access-token [params]
  (println "access token")
  (oauth2/get-access-token google-com-oauth2 params auth-req))



