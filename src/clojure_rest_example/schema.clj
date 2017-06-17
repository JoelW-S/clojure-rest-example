(ns clojure-rest-example.schema
  (:require [schema.core :as s])
  (:require [clojure.set :refer [map-invert]])
  (:import org.bson.types.ObjectId))

(def non-empty-string (s/pred #(and (instance? java.lang.String %) (not (empty? %)))))

(def user {(s/optional-key :_id) org.bson.types.ObjectId
           :name                non-empty-string
           :email               non-empty-string})

(defn convert-to-error [m]
  {:error (->> m
               (reduce (fn [total [k v]] (conj total {k v})) [])
               (map map-invert)
               (map (fn [m]
                      (reduce-kv (fn [m k v]
                                   (assoc m (clojure.string/replace k "-" "_") [v])) {} m)))
               (apply (partial merge-with into {})))})

(defn create-response
  ([status]
   {:status status})
  ([status body]
   {:status status
    :body body}))

(defn model->response [model]
  (cond
    (or (nil? model) (empty? model)) (create-response 400)
    (:error model)  (create-response 400 model)
    :else (create-response 200 model)))

(defn verify-model [schema]
  (fn [request] (->> (:body request)
                     ((fn [request-body] (if-let [err (s/check schema request-body)]
                                           (->  err
                                                convert-to-error)
                                           request-body))))))

(def validate-user (verify-model user))
