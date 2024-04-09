(ns code)

(def submitted-code-snippet
  "
(ns solution 
  (:require [clojure.pprint :as pprint]
            [clojure.string :as string]))

(defn avg-fn [a b c]
  (/ (+ (* a 2.0) (* b 3.0) (* c 5.0)) 10.0))

(defn main []
  (let [n (Double/parseDouble (read-line))]
    (loop [i 1]
      (when (<= i n)
        (let [s (string/split (read-line) #",")
              input (mapv #(Double/parseDouble %) s)
              [a b c] input]
          (pprint/pprint {:test-case i
                          :result (avg-fn a b c)}))
        (recur (inc i))))))

(main)
  ")

(def test-cases {:test-1 {:input "1,2,3" :output 2.1}
                 :test-2 {:input "2,3,4" :output 3.1}
                 :test-3 {:input "3,4,5" :output 4.1}
                 :test-4 {:input "4,5,6" :output 5.1}
                 :test-5 {:input "5,6,7" :output 6.2}})

(defn make-input [test-case]
  (let [n (count test-cases)]
    (reduce (fn [acc [k v]]
              (let [input (apply str (:input v) "\n")]
                (str acc input)))
            (str n "\n")
            test-case)))

(def csv-input (make-input test-cases))
