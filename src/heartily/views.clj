(ns heartily.views
  (:require [hiccup.core :refer [html h]]))


(defn login-page [url]
  (html [:a {:href url} "Connect to Google Fit!"]))

(defn index-page [token]
  (html
   [:ul
    ;[:li [:a {:href "/initialize"} "Initialize (for first time users)!"]]
    [:li [:a {:href "/fit?url=/dataSources/derived:com.google.calories.expended:com.google.android.gms:merge_calories_expended"} "Fun Stuff"]]]))
