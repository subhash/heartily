(ns heartily.gpx
  (:require [clojure.xml :as xml]
            [clojure.zip :use [xml-zip down right node]]
            [datomic.api :as d]))


(def trk-mapping {:track/name [:content 0 :content 0]})

(def trkpt-mapping {:track-point/latitude [:attrs :lat]
                    :track-point/longitude [:attrs :lon]
                    :track-point/altitude [:content 2 :content 3 :content 0]
                    :track-point/heart-rate [:content 2 :content 0 :content 0 :content 0]
                    :track-point/speed [:content 2 :content 6 :content 0]
                    :track-point/vertical-speed [:content 2 :content 7 :content 0]})


(defn load-data [file]
  (xml/parse (java.io.File. file)))


(defn find-trk [data]
  (letfn [(trkpt [t] (into {} (map (fn [[id index]] [id (-> (get-in t index) Float/parseFloat )]) trkpt-mapping)))]
    (let [trkpts (->> data xml-zip down down right node :content (map trkpt)
                      (map #(merge % {:db/id (d/tempid :db.part/user)})))]
      {:db/id (d/tempid :db.part/user)
       :track/name (-> data xml-zip down down node :content first)
       :track/track-points trkpts})))
