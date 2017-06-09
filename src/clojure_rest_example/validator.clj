(ns clojure-rest-example.validator
  (:require [clojure.data :refer [diff]]))

(defn valid-schema? [schema structure]
  "Compares schema against structure, returns true if the same structure exist in both, ignoring additional keys."
  (let [difference (diff schema structure)]
    (= schema (into {} (nth difference 2)))))
