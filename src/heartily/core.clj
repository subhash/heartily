(ns heartily.core
  (:require [heartily.db :as hdb]
            [heartily.gpx :as gpx]))

(defn load-workout [gpxfile]
  (let [data (gpx/load-data gpxfile)
        trk (gpx/find-trk data)]
    (hdb/init)
    (hdb/load-trk trk)
    (ffirst (hdb/dump-trk))))


(defn heart-rates [workout]
  (->> workout :track/track-points (map :track-point/heart-rate)))

