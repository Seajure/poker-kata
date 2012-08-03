(ns poker-kata.core-test
  (:use clojure.test
        poker-kata.core))

(deftest parsing-card
  (is (= [2 :hearts] (parse-card "2H")))
  (is (= [10 :clubs] (parse-card "TC")))
  (is (= [14 :spades] (parse-card "AS"))))

(deftest parsing-hands
  (is (= [[2 :hearts] [3 :diamonds] [5 :spades] [9 :clubs] [13 :diamonds]]
         (parse-hand "2H 3D 5S 9C KD"))))

(deftest sorting-cards
  (is (= [[2 :diamonds] [4 :diamonds]]
         (sort-cards [[4 :diamonds] [2 :diamonds]])))
  (is (= [[5 :hearts] [5 :spades]]
         (sort-cards [[5 :spades] [5 :hearts]]))))

(deftest finding-the-highest-value
  (is (= [10 :spades]
         (highest [[10 :spades] [10 :diamonds]])))
  (is (= [10 :hearts]
         (highest [[5 :spades] [10 :hearts]]))))


(deftest categorize-hands
  (is (= :high-card
         (categorize-hand "2H 3S 4S 5C 7H")))
  (is (= :straight
         (categorize-hand "2H 3S 4S 5C 6H"))))

(deftest comparing-hands
  (is (= 1
         (compare-hands "2H 3S 4S 5C 6H" "3H 4S 5S 6C 7H"))))
