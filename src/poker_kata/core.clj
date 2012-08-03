(ns poker-kata.core)

(def face-values {\2 2 \3 3 \4 4 \5 5 \6 6 \7 7 \8 8 \9 9 \T 10 \J 11 \Q 12 \K 13 \A 14})
(def suits {\D :diamonds \C :clubs \H :hearts \S :spades})
(def suit-value {:diamonds 0 :clubs 1 :hearts 2 :spades 3})

(defn parse-card [[value suit]]
  (vector (face-values value)
          (suits suit)))

(defn compare-cards [[v1 s1] [v2 s2]]
  (if (= v1 v2)
    (compare (suit-value s1) (suit-value s2))
    (compare v1 v2)))

(defn sort-cards [cards]
  (sort compare-cards cards))

(defn parse-hand [s]
  (sort-cards (mapv parse-card (.split s " "))))

(defn highest [cards]
  (last (sort-cards cards)))

(defn high-card? [cards]
  :high-card)

(defn straight? [[[lowest-val _] & rest-of-cards :as all-the-cards]]
  (when (= (map first all-the-cards)
           (take (count all-the-cards) (iterate inc lowest-val)))
    :straight))

(def foo [[:straight straight?] [:high-card high-card?]])

(defn categorize-hand [hand]
  (some #(% (parse-hand hand)) (map second foo)))

(defmulti tie-break (fn [h _] (categorize-hand h)))

(defmethod tie-break :high-card [h1 h2]
  (drop-while zero? (map compare-cards (reverse h1) (reverse h2))))

(defmethod tie-break :straight [[f1 & _] [f2 & _]]
  (compare-cards f1 f2))

(defn compare-hands [hand1 hand2]
  (let [category1-value (.indexOf (map first foo) (categorize-hand hand1))
        category2-value (.indexOf (map first foo) (categorize-hand hand2))]
    (if (= category1-value category2-value)
      (tie-break (parse-hand hand1) (parse-hand hand2))
      (compare category1-value category2-value))))
