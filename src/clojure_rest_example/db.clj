(ns clojure-rest-example.db
  (:require  [monger.core :as mg]
             [monger.collection :as mc]
             [environ.core :refer  [env]]
             [monger.credentials :as mcr]
             [monger.result :refer  [acknowledged?]])
  (:import  [com.mongodb MongoOptions ServerAddress DuplicateKeyException])
  (:import org.bson.types.ObjectId))

(def database-url (env :database-url))

(def database-port (Integer. (env :database-port)))

(def database-name (env :database-name))

(def database-user (env :database-user))

(def database-password (env :database-password))

(def database
  (let  [conn  (mg/connect  {:host database-url :port database-port})
         db (mg/get-db conn database-name)]
    db))

(defn fetch  [collection id]
  (mc/find-one-as-map database collection id))

(defmacro handle-duplicate-key [& body]
  `(try ~@body
        (catch DuplicateKeyException e#
          nil)))

(defn persist
  [model collection]
  (let [unique-id (ObjectId.)]
    (handle-duplicate-key (if (acknowledged? (mc/insert database collection  (assoc model :_id unique-id)))
                            (fetch collection {:_id unique-id})))))
