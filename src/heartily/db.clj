(ns heartily.db
  (:require [datomic.api :as d]
            [clojure.java.io :as io]))

(def uri "datomic:mem://heartily")

(def schema-tx
  (read-string (slurp (io/resource "db/schema.edn"))))

(d/create-database uri)

(def conn (d/connect uri))

@(d/transact conn schema-tx)

(defn save-activity [a]
  @(d/transact conn [a]))





