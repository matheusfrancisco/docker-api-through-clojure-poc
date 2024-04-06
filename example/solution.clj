(ns solution
  (:require
   [clojure.pprint :as pprint]))

(defn avg-fn [a b c]
  (/ (+ (* a 2.0) (* b 3.0) (* c 5.0)) 10.0))

(defn main []
  (let [a (Double/parseDouble (read-line))
        b (Double/parseDouble (read-line))
        c (Double/parseDouble (read-line))]
    {#{a, b, c} (format "%.1f" (avg-fn a b c))}))

(pprint/pprint (main))
