(ns heartily.core
  (:require [heartily.db :as hdb]
            [heartily.gpx :as gpx]))

(defn load-gpx [gpxfile]
  (let [data (gpx/load-data gpxfile)
        trk (gpx/find-trk data)]
    (hdb/init)
    (hdb/load-trk trk)
    (hdb/dump-trk)))





