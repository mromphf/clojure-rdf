(ns rdf.core
  (:gen-class))

(require '[rdf.query :as query])

(defn -main
  [& args]
  (println (query/find-subject-by-name "Mark")))
