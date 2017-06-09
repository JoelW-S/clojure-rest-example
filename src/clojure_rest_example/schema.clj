(ns clojure-rest-example.schema
  (:require [schema.core :as s])
  (:require [clojure.set :refer [map-invert]]))

(def user {
           (s/optional-key :id) s/Uuid
           :name                s/Str
           :email               s/Str
           })

(defn convert-to-error [m]
  {:error (->> m
               (reduce (fn [total [k v]] (conj total {k v})) [])
               (map map-invert)
               (map (fn [m]
                      (reduce-kv (fn [m k v]
                                   (assoc m (clojure.string/replace k "-" "_") [v])) {} m)))
               (apply (partial merge-with into {})))})

(defn model->response
  ([model]
   (if-not (:error model)
     {:status 200
      :body   model}
     {:status 400
      :body   model})))

(defn verify-model [schema]
  (fn [request] (->> (:body request)
                     ((fn [request-body] (if-let [err (s/check schema request-body)]
                                           (convert-to-error err)
                                           request-body))))))

(def validate-user (verify-model user))

