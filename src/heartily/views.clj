(ns heartily.views
  (:require [hiccup.core :refer [html h]]))


(defn index-page [url]
  (html [:a {:href url} "Connect to Google Fit!"]))
