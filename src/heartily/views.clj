(ns heartily.views
  (:require [hiccup.core :refer [html h]]))


(defn login-page [url]
  (html [:a {:href url} "Connect to Google Fit!"]))

(defn index-page [hr email]
  (html
   [:div
    [:ul
    [:li (str "Welcome " email)]
    [:li [:a {:href "/fit?url=/dataSources/derived:com.google.calories.expended:com.google.android.gms:merge_calories_expended"} "Fun Stuff"]]
    [:li (str hr)]
    [:li [:a {:href "/fit?url=/dataSources/raw:com.google.heart_rate.bpm:776188546157:/datasets/1409589129450000000-1409596328020000000"} "Loaded data!"]]]
    [:form {:action "/upload" :method "post" :enctype "multipart/form-data"}
     [:input {:name "file" :type "file" :size 20}]
     [:input {:name "submit" :type "submit" :value "Load data!"}]]]))
