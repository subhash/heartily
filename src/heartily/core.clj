(ns heartily.core
  (:require [heartily.db :as hdb]
            [heartily.gpx :as gpx]))

(defn load-gpx [gpxfile]
  (let [data (gpx/load-data gpxfile)
        trkpts (gpx/find-trkpts data)]
    (hdb/init)
    (hdb/load-trkpts trkpts)))





