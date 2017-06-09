(ns clojure-rest-example.db
  (:require [couchbase-clj.client :as c]))

(c/defclient client {:bucket     "travel-sample"
                     :uris       ["http://couchbase-master-service:8091/pools/"]
                     :op-timeout 10000})

(defn uuid [] (.toString (java.util.UUID/randomUUID)))

(defn persist
  [model]
  (let [unique-id (uuid)]
    (c/add client (str {:email (:email model)}) unique-id)
    (c/add-json client unique-id model)))
