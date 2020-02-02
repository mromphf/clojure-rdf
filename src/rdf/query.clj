(ns rdf.query
  (:gen-class))

(def data (read-string (slurp "data.edn")))

(defn subject-by-name [n]
  (first (map :subject 
              (filter 
                (fn [row] (= (:object row) n)) data))))

(defn predicate-by-subject [predicate subject]
  (map :object (filter 
                 (fn [row] (and 
                           (= predicate (:predicate row)) 
                           (= subject (:subject row)) )) data)))

(defn group-common-objects [predicate subject]
  {predicate (predicate-by-subject predicate subject)})

(defn all-predicates-by-subject [subject]
  (sort (distinct (map :predicate (filter
                    (fn [row] (= subject (:subject row))) data)))))

(defn records-by-subject [subject]
  (filter 
    (fn [row] (= (:subject row) subject)) data))

