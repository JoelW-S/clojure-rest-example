(ns clojure-rest-example.operation
  (:require [clojure-rest-example.schema :refer [validate-user model->response]])
  (:require [clojure-rest-example.db :as db]))

(defn create-user [request]
  (let [user (validate-user request)]
    (if (:error user)
      (model->response user)
      (do
        (db/persist user)
        (model->response user)))))
