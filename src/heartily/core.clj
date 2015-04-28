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

(defn heart-beats-for [activity]
  "heart beats sorted by time"
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

(defn heart-beat-stats []
  "Vectors of min avg and max heart beats sorted by activity time"
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
       (#(partition (/ (count %) 3) %))
       ))

(defn heart-beat-std []
  (->> (d/q '[:find ?n (min ?tm) (stddev ?hr)
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
       ))


(defn activity-stats []
  (->> (d/q '[:find ?n (min ?tm) (max ?tm)
              :where
              [?a :activity/name ?n]
              [?a :activity/track ?t]
              [?t :track/track-points ?tp]
              [?tp :track-point/heart-rate ?hr]
              [?tp :track-point/time ?tt]
              [(.getTime ?tt) ?tm]]
            (d/db hdb/conn))
       (sort-by second)
       (map (fn [[_ a b]] (/ (- b a) 60000)))
       (filter (partial < 5))))


