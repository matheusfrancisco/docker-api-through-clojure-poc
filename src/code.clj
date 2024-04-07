(ns code)

(def submitted-code-snippet
  "(ns solution
     )
     (defn avg-fn [a b c]
       (/ (+ (* a 2.0) (* b 3.0) (* c 5.0)) 10.0))
     
     
     ;; add-main-here
     ")

(def template-snippet
  " (defn main []
       (let [a (Double/parseDouble (read-line))
             b (Double/parseDouble (read-line))
             c (Double/parseDouble (read-line))]
         {#{a, b, c} (format \"%.1f\" (avg-fn a b c))}))
  (println (main))")

(defn merge-code-snippets [submitted-snippet template-snippet]
  (let [placeholder ";; add-main-here"
        ;; Split the submitted snippet at the placeholder
        [before after] (clojure.string/split submitted-snippet (re-pattern (java.util.regex.Pattern/quote placeholder)))
        ;; Remove the call to (main) from the template snippet, assuming it's always at the end.
        ]
    ;; Concatenate the before part, the cleaned template, and the after part.
    (str before template-snippet after)))

(def merged-code (merge-code-snippets submitted-code-snippet template-snippet))
