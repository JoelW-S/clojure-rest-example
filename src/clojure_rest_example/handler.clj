(ns clojure-rest-example.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [schema.core :as s]
            [clojure-rest-example.operation :as op]
            [taoensso.timbre :refer  [info]])
  (:require monger.json))

(defn basic-logging-middleware [handler]
  (fn [request]
    (info "Payload:" request)
    (let [response (handler request)]
      (info "Response:" response)
      response)))

(defroutes app-routes
  (context "/user" []
    (GET "/" request (op/get-users))
    (POST "/" request (op/create-user request))
    (DELETE "/:email" [email] (op/delete-user email))
    (GET "/:email" [email]  (op/get-user email)))
  (route/not-found "Page not found"))

(def routes-with-middleware
  (-> app-routes
      basic-logging-middleware
      (middleware/wrap-json-body {:keywords? true})
      middleware/wrap-json-response))

(def app (handler/site routes-with-middleware))
