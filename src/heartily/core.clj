(ns heartily.core
  (:require [heartily.db :as hdb]
            [heartily.gpx :as gpx]
            [datomic.api :as d]))

(defn store-activity [name gpxfile]
  (hdb/save-activity (gpx/gpx->map name gpxfile)))

(store-activity "trek1" "resources/data/trek1.gpx")
(store-activity "trek2" "resources/data/trek2.gpx")
(store-activity "trek3" "resources/data/trek3.gpx")
(store-activity "trek4" "resources/data/trek4.gpx")

(defn heart-beats-for [activity]
  (->> (d/q '[:find ?tm ?hr
              :in $ ?n
              :where
              [?a :activity/name ?n]
              [?a :activity/track ?t]
              [?t :track/track-points ?tp]
              [?tp :track-point/heart-rate ?hr]
              [?tp :track-point/time ?tt]
              [(.getTime ?tt) ?tm]]
       (d/db hdb/conn) activity)
       (sort-by first)
       (map second)))
