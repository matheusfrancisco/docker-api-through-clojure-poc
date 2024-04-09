(ns solution
  (:require
   [clojure.pprint :as pprint]
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
                          :result (format "%.1f" (avg-fn a b c))}))
        (recur (inc i))))))

(main)
