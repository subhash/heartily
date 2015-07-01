(defproject heartily "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.zip "0.1.1"]
                 [com.datomic/datomic-free "0.9.5186" :exclusions [joda-time]]
                 [compojure "1.1.5"]
                 [hiccup "1.0.5"]
                 [stuarth/clj-oauth2 "0.3.2"]]
  :plugins [[lein-gorilla "0.3.4"]
            [lein-vanity "0.2.0"]
            [lein-ring "0.8.5"]]
  :ring {:handler heartily.server/app}
  :source-paths ["src/"]
  :main ^{:skip-aot true} heartily.server
)
