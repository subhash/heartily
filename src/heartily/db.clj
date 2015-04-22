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


(defn load-trk [t]
  (let [conn (d/connect uri)]
    @(d/transact conn [t])))

(defn dump-trk []
  (let [conn (d/connect uri)]
    (d/q '[:find (pull ?a [* {:track/track-points [*]}]) :where [?a :track/name ?e]] (d/db conn))))




