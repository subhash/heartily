(ns heartily.views
  (:require [hiccup.core :refer [html h]]))

(def heartrate-uri "/fit?url=/dataSources/raw:com.google.heart_rate.bpm:776188546157:/datasets")

(defn start-time [a]
  (str (java.util.Date. (/ (Long/parseLong (a "startTimeNanos")) 1000 1000))))

(defn login-page [url]
  (html [:a {:href url} "Connect to Google Fit!"]))

(defn index-page [activities email strava-link]
  (let []
    (html
     [:div
      [:ul
       [:li (str "Welcome " email)]
       [:li [:a {:href strava-link} "Connect to Strava"]]
       [:form {:action "/upload" :method "post" :enctype "multipart/form-data"}
        [:input {:name "file" :type "file" :size 20}]
        [:input {:name "submit" :type "submit" :value "Load data!"}]]
       [:ul (str (count activities) " Workouts: ")
        (for [a activities]
          [:li [:a {:href (str heartrate-uri "/" (a "startTimeNanos") "-" (a "endTimeNanos"))} (start-time a)]])]]])))


(defn strava-page [activities]
  (let []
    (html
     [:div
      [:ul (count activities)
       (for [a activities]
         [:li [:div (str (:name a) " - " (:description a))]])]
      [:a {:href "/"} "<< Back to Safety!"]])))
