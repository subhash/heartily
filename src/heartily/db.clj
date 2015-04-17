(ns heartily.db
  (:require [datomic.api :as d]
            [clojure.java.io :as io]))

(def uri "datomic:mem://heartily")

(def schema-tx
  (read-string (slurp (io/resource "db/schema.edn"))))

(defn init []
  (d/create-database uri)
  (let [conn (d/connect uri)]
    @(d/transact conn schema-tx)
    (d/q '[:find ?n :where [:db.part/db :db.install/attribute ?a] [?a :db/ident ?n]] (d/db conn))))


(defn load-trkpts [t]
  (let [conn (d/connect uri)
        data (map #(merge % {:db/id (d/tempid :db.part/user)}) t)
        foo (println (first data))]
    @(d/transact conn data)))

(defn all-altitudes []
  (let [conn (d/connect uri)]
    (d/q '[:find ?a :where [?e :track-point/altitude ?a]] (d/db conn))))


