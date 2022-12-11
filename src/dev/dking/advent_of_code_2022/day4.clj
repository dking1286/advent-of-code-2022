(ns dev.dking.advent-of-code-2022.day4
  (:require [clojure.java.io :as io]
            [clojure.set :as set]
            [clojure.string :as string]))

(defn- get-sections
  [range-str]
  (let [[start stop] (map #(Integer/parseInt %) (string/split range-str #"-"))]
    (set (range start (inc stop)))))

(defn- one-range-is-redundant
  [line]
  (let [[left right] (string/split line #",")
        left-sections (get-sections left)
        right-sections (get-sections right)]
    (or (set/subset? left-sections right-sections)
        (set/subset? right-sections left-sections))))

(defn- get-overlap
  [line]
  (let [[left right] (string/split line #",")
        left-sections (get-sections left)
        right-sections (get-sections right)]
    (or (seq (set/intersection left-sections right-sections))
        (seq (set/intersection right-sections left-sections)))))

(defn part1
  []
  (with-open [rdr (io/reader (io/resource "day4.txt"))]
    (let [lines (line-seq rdr)]
      (->> lines
           (filter one-range-is-redundant)
           count))))

(defn part2
  []
  (with-open [rdr (io/reader (io/resource "day4.txt"))]
    (let [lines (line-seq rdr)]
      (->> lines
           (filter get-overlap)
           count))))

(comment
  (range 2 (inc 4))

  (def range-str "2-8")

  (let [[start stop] (map #(Integer/parseInt %) (string/split range-str #"-"))
        sections (set (range start (inc stop)))]
    sections)

  (apply range (map #(Integer/parseInt %) (string/split range-str #"-")))

  (def line "2-8,3-7")

  (let [[left right] (string/split line #",")
        left-sections (get-sections left)
        right-sections (get-sections right)]
    (or (set/subset? left-sections right-sections)
        (set/subset? right-sections left-sections)))

  (with-open [rdr (io/reader (io/resource "day4.txt"))]
    (let [lines (line-seq rdr)]
      (->> lines
           (filter one-range-is-redundant)
           count)))

  (let [[left right] (string/split line #",")
        left-sections (get-sections left)
        right-sections (get-sections right)]
    (or (seq (set/intersection left-sections right-sections))
        (seq (set/intersection right-sections left-sections))))

  (with-open [rdr (io/reader (io/resource "day4.txt"))]
    (let [lines (line-seq rdr)]
      (->> lines
           (filter get-overlap)
           count))))