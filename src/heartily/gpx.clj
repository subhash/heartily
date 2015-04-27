(ns heartily.gpx
  (:require [clojure.xml :as xml]
            [clojure.zip :refer [xml-zip down right node]]
            [clojure.data.zip.xml :refer [xml-> xml1-> attr text]]
            [datomic.api :as d]
            [clojure.instant]))


(defn track-point->map [z]
  (let [tp {:track-point/latitude (xml1-> z (attr :lat) #(Float/valueOf %))
            :track-point/longitude (xml1-> z (attr :lon) #(Float/valueOf %))
            :track-point/altitude (xml1-> z :extensions (keyword "gpxdata:altitude") text #(Float/valueOf %))
            :track-point/heart-rate (xml1-> z :extensions (keyword "gpxtpx:TrackPointExtension") (keyword "gpxtpx:hr") text #(Integer/valueOf %))
            :track-point/speed (xml1-> z :extensions (keyword "gpxdata:speed") text #(Float/valueOf %))
            :track-point/vertical-speed (xml1-> z :extensions (keyword "gpxdata:verticalSpeed") text #(Float/valueOf %))
            :track-point/time (xml1-> z :time text #(clojure.instant/read-instant-date %))
            :db/id (d/tempid :db.part/user)}]
    (->> tp (filter val) (into {}))))

(defn track->map [z]
  (let [track-points (xml-> z :trk :trkseg :trkpt)]
    {:track/name (xml1-> z :trk :name text)
     :track/track-points (mapv track-point->map track-points)
     :db/id (d/tempid :db.part/user)}))


(defn gpx->map [name file]
  {:activity/name name
   :activity/track (-> (java.io.File. file) xml/parse xml-zip track->map)
   :db/id (d/tempid :db.part/user)})







