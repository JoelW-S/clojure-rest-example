(ns clojure-rest-example.db
  (:require  [monger.core :as mg]
             [monger.collection :as mc]
             [environ.core :refer  [env]]
             [monger.credentials :as mcr]
             [monger.result :refer  [acknowledged?]]
             [clojure-rest-example.response :as res])
  (:import  [com.mongodb MongoOptions ServerAddress DuplicateKeyException])
  (:import org.bson.types.ObjectId))

(def database-url (env :database-url))

(def database-port (Integer. (env :database-port)))

(def database-name (env :database-name))

(def database
  (let  [conn  (mg/connect  {:host database-url :port database-port})
         db (mg/get-db conn database-name)]
    db))

(defn fetch
  ([collection]
   (if-let [document (mc/find-maps database collection)]
     document
     res/fetch-error))
  ([collection id]
   (if-let [document (mc/find-one-as-map database collection id)]
     document
     res/fetch-error)))

(defmacro handle-duplicate-key [& body]
  `(try ~@body
        (catch DuplicateKeyException e#
          `res/entity-exists-error)))

(defn persist
  [model collection]
  (let [unique-id (ObjectId.)]
    (handle-duplicate-key (if (acknowledged? (mc/insert database collection  (assoc model :_id unique-id)))
                            (fetch collection {:_id unique-id})
                            res/persist-error))))

(defn delete
  [collection id]
  (let [write-result (.getN (mc/remove database collection id))]
    (if (= 1 write-result)
      res/delete-success
      res/delete-error)))
