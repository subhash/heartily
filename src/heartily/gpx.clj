(ns heartily.gpx
  (:require [clojure.xml :as xml]
            [clojure.zip :use [xml-zip down right node]]))


(def trkpt-mapping {:track-point/latitude [:attrs :lat]
                    :track-point/longitude [:attrs :lon]
                    :track-point/altitude [:content 2 :content 3 :content 0]})


(defn load-data [file]
  (xml/parse (java.io.File. file)))


(defn find-trkpts [data]
  (letfn [(trkpt [t] (into {} (map (fn [[id index]] [id (-> (get-in t index) Float/parseFloat )]) trkpt-mapping)))]
    (let [trkpts (-> data xml-zip down down right node :content)
          mapped (map trkpt trkpts)]
              mapped)))
