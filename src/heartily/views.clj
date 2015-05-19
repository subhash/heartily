(ns heartily.views
  (:require [hiccup.core :refer [html h]]))


(defn login-page [url]
  (html [:a {:href url} "Connect to Google Fit!"]))

(defn index-page [token]
  (str "token " token))
