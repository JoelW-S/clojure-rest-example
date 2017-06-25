(ns clojure-rest-example.response)

(def delete-success {:reason "Delete success"})

(def fetch-error {:error "Retrieval could not be performed"})

(def delete-error {:error "Deletion could not be performed"})

(def persist-error {:error "Persistence could not be performed"})

(def entity-exists-error {:error "Enitity already exists"})
