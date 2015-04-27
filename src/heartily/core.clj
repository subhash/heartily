(ns heartily.core
  (:require [heartily.db :as hdb]
            [heartily.gpx :as gpx]
            [datomic.api :as d]))

(defn store-activity [name gpxfile]
  (hdb/save-activity (gpx/gpx->map name (java.io.File. gpxfile))))

(defn init []
  (doseq [f (file-seq (java.io.File. "resources/data/"))
        :let [fname (.getName f) fpath (.getPath f)]
        :when (re-matches #".*\.gpx" fname)]
    (store-activity fname fpath)))

; heart beats sorted by time
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

; vectors of avg max and min heart beats sorted by activity time
(defn heart-beat-stats []
  (->> (d/q '[:find ?n (min ?tm) (min ?hr) (avg ?hr) (max ?hr)
              :where
              [?a :activity/name ?n]
              [?a :activity/track ?t]
              [?t :track/track-points ?tp]
              [?tp :track-point/heart-rate ?hr]
              [?tp :track-point/time ?tt]
              [(.getTime ?tt) ?tm]]
            (d/db hdb/conn))
       (sort-by second)
       (map (partial drop 2))
       (apply interleave)
       (partition 3)))


