(ns heartily.core
  (:require [heartily.db :as hdb]
            [heartily.gpx :as gpx]))

(defn store-activity [name gpxfile]
  (hdb/save-activity (gpx/gpx->map name gpxfile)))

