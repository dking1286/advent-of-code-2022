(ns dev.dking.advent-of-code-2022.day3
  (:require [clojure.set :as set]
            [clojure.string :as string]
            [clojure.java.io :as io]))

(def priority-order
  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")

(def priority
  (into {} (map-indexed (fn [i char] [char (inc i)])) priority-order))

(defn part1
  []
  (with-open [rdr (io/reader (io/resource "day3.txt"))]
    (let [lines (line-seq rdr)]
      (->> lines
           (map (fn [line] [(subs line 0 (/ (count line) 2))
                            (subs line (/ (count line) 2))]))
           (map (fn [[first-half second-half]] (set/intersection (set first-half)
                                                                 (set second-half))))
           (mapcat identity)
           (map priority)
           (reduce +)))))

(defn part2
  []
  (with-open [rdr (io/reader (io/resource "day3.txt"))]
    (let [lines (line-seq rdr)]
      (->> lines
           (partition 3)
           (map #(map set %))
           (map #(reduce set/intersection %))
           (mapcat identity)
           (map priority)
           (reduce +)))))

(comment
  (def test-input
    "vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw")
  (def lines (string/split-lines test-input))
  (set "abcdd")
  (def line "vJrwpWtwJgWrhcsFMMfFFhFp")

  (set/intersection (set (subs line 0 (/ (count line) 2)))
                    (set (subs line (/ (count line) 2))))

  (for [line (string/split-lines test-input)]
    (println (set/intersection (set (subs line 0 (/ (count line) 2)))
                               (set (subs line (/ (count line) 2))))))

  (with-open [rdr (io/reader (io/resource "day3.txt"))]
    (let [lines (line-seq rdr)]
      (->> lines
           (map (fn [line] [(subs line 0 (/ (count line) 2))
                            (subs line (/ (count line) 2))]))
           (map (fn [[first-half second-half]] (set/intersection (set first-half)
                                                                 (set second-half))))
           (mapcat identity)
           (map priority)
           (reduce +))))

  (with-open [rdr (io/reader (io/resource "day3.txt"))]
    (let [lines (line-seq rdr)]
      (->> lines
           (partition 3)
           (map #(map set %))
           (map #(reduce set/intersection %))
           (mapcat identity)
           (map priority)
           (reduce +)))))