(defproject heartily "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.datomic/datomic-pro "0.9.5130"]]
  :plugins [[lein-gorilla "0.3.4"]]
  :source-paths ["src/"]
  ;:main ^:skip-aot gorilla-test.core
  :main ^{:skip-aot true} heartily.core
)
