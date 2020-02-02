(ns rdf.query
  (:gen-class))

(def data (read-string (slurp "data.edn")))

(defn subject-by-name [n]
  (first (map :subject 
              (filter 
                (fn [row] (= (:object row) n)) data))))

(defn records-by-subject [subject]
  (filter
    (fn [row] (= (:subject row) subject)) data))

(defn subject-predicates [subject]
  (set (map :predicate (filter
                         (fn [row] (= subject (:subject row))) data))))

(defn predicate-by-subject [predicate subject]
  (def result-set (map :object (filter
                 (fn [row] (and 
                           (= predicate (:predicate row)) 
                           (= subject (:subject row)) )) data)))
  (if (= 1 (count result-set))
    (first result-set)
    (set result-set)))

(defn compose-entity [subject]
  (apply merge-with (comp flatten vector)
    (map (fn [p]
         {p (predicate-by-subject p subject)})
         (subject-predicates subject))))
