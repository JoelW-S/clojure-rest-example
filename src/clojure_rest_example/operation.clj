(ns clojure-rest-example.operation
  (:require [clojure-rest-example.schema :refer [validate-user model->response]])
  (:require [clojure-rest-example.db :as db]))

(def user-collection "user")

(defn wrap-id-in-map  [k id]
  (hash-map k id))

(defn create-user [request]
  (let [user (validate-user request)]
    (if-let [err (:error user)]
      (model->response user)
      (-> (db/persist user user-collection)
          model->response))))

(defn get-user [email]
  (-> (db/fetch user-collection (wrap-id-in-map :email email))
      model->response))
