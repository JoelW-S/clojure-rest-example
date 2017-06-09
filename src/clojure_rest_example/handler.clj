(ns clojure-rest-example.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler])
  (:require [ring.middleware.json :as middleware])
  (:require [schema.core :as s])
  (:require [clojure-rest-example.operation :refer [create-user]])
  (:require [taoensso.timbre :refer [info]]))

(defn basic-logging-middleware [handler]
  (fn [request]
    (info "Payload:" request)
    (let [response (handler request)]
      (info "Response:" response)
      response)))

(defroutes app-routes
           (POST "/user" request (create-user request)))

(def routes-with-middleware
  (-> app-routes
      basic-logging-middleware
      (middleware/wrap-json-body {:keywords? true})
      middleware/wrap-json-response))

(def app (handler/site routes-with-middleware))
