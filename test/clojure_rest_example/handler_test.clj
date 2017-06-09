(ns clojure-rest-example.handler-test
  (:use clojure.test
        ring.mock.request
        clojure-rest-example.handler))


(deftest test-app
  (testing "Hello Endpoint"
    (let [response (app (request :get "/hello/some_name"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Hello some_name"))))
  (testing "Reverse Endpoint"
    (let [response (app (request :get "/reverse/word"))]
      (is (= (:status response) 200))
      (is (= (:body response) "drow")))))
