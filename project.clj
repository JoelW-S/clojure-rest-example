(defproject clojure-rest-example "0.1.0-SNAPSHOT"
  :description "Clojure Rest service example"
  :url ""
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-core "1.6.1"]
                 [ring/ring-jetty-adapter "1.6.1"]
                 [compojure "1.6.0"]
                 [ring/ring-json "0.4.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [metosin/ring-http-response "0.9.0"]
                 [prismatic/schema "1.1.6"]
                 [com.novemberain/monger "3.1.0"]
                 [environ "1.1.0"]
                 [cheshire "5.1.1"]
                 ]
  :plugins [[lein-ring "0.12.0"]]
  :ring {:handler clojure-rest-example.handler/app})
