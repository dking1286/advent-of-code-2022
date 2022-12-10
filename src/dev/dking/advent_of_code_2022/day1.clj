(ns dev.dking.advent-of-code-2022.day1
  (:require [clojure.java.io :as io]))

(defn part1
  []
  (with-open [rdr (io/reader (io/resource "day1_part1.txt"))]
    (let [lines (line-seq rdr)
          {:keys [current best]}
          (->> lines
               (reduce (fn [{:keys [current best]} next]
                         (if (= next "")
                           {:current 0
                            :best (max current best)}
                           {:current (+ current (Integer/parseInt next))
                            :best best}))
                       {:current 0 :best 0}))]
      (max current best))))

(defn part2
  []
  (with-open [rdr (io/reader (io/resource "day1_part1.txt"))]
    (let [lines (line-seq rdr)
          {:keys [current best]}
          (->> lines
               (reduce (fn [{:keys [current best]} next]
                         (if (= next "")
                           {:current 0 :best (->> (conj best current)
                                                  (sort >)
                                                  (take 3))}
                           {:current (+ current (Integer/parseInt next)) :best best}))
                       {:current 0 :best [0 0 0]}))
          best-3 (->> (conj best current)
                      (sort >)
                      (take 3))]
      (reduce + best-3))))

(comment
  (+ 2 2)
  (slurp (io/resource "day1_part1.txt"))
  ;; Part 1
  (with-open [rdr (io/reader (io/resource "day1_part1.txt"))]
    (let [lines (line-seq rdr)
          {:keys [current best]}
          (->> lines
               (reduce (fn [{:keys [current best]} next]
                         (if (= next "")
                           {:current 0 :best (max current best)}
                           {:current (+ current (Integer/parseInt next)) :best best}))
                       {:current 0 :best 0}))]
      (max current best)))
  ;; Part 2
  (* 3 66616)
  (let [current 5
        best [1 3 10]]
    (->> (conj best current)
         (sort >)
         (take 3)))
  (with-open [rdr (io/reader (io/resource "day1_part1.txt"))]
    (let [lines (line-seq rdr)
          {:keys [current best]}
          (->> lines
               (reduce (fn [{:keys [current best]} next]
                         (if (= next "")
                           {:current 0 :best (->> (conj best current)
                                                  (sort >)
                                                  (take 3))}
                           {:current (+ current (Integer/parseInt next)) :best best}))
                       {:current 0 :best [0 0 0]}))
          best-3 (->> (conj best current)
                      (sort >)
                      (take 3))]
      (reduce + best-3))))