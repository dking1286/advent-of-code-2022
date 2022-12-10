(ns dev.dking.advent-of-code-2022.day2
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def moves
  {:rock {:left-name :A
          :right-name :X
          :score 1
          :defeats :scissors}
   :paper {:left-name :B
           :right-name :Y
           :score 2
           :defeats :rock}
   :scissors {:left-name :C
              :right-name :Z
              :score 3
              :defeats :paper}})

(def moves-by-left-name
  (into {} (map (fn [[move {:keys [left-name]}]] [left-name move])) moves))

(def moves-by-right-name
  (into {} (map (fn [[move {:keys [right-name]}]] [right-name move])) moves))

(def defeats
  (into {} (map (fn [[move {:keys [defeats]}]] [defeats move])) moves))

(def defeated-by
  (into {} (map (fn [[k v]] [v k])) defeats))

(def results
  {:loss {:score 0
          :name :X}
   :draw {:score 3
          :name :Y}
   :win {:score 6
         :name :Z}})

(def results-by-name
  (into {} (map (fn [[result {:keys [name]}]] [name result])) results))

(defn- get-round-score
  [line]
  (let [[left right] (map keyword (string/split line #" "))
        my-move (moves-by-right-name right)
        opponent-move (moves-by-left-name left)
        result (cond
                 (= my-move opponent-move) :draw
                 (= (-> moves my-move :defeats) opponent-move) :win
                 (= (-> moves opponent-move :defeats) my-move) :loss)]
    (+ (-> moves my-move :score)
       (-> results result :score))))

(defn part1
  []
  (with-open [rdr (io/reader (io/resource "day2.txt"))]
    (->> (line-seq rdr)
         (map get-round-score)
         (reduce +))))

(defn- get-correct-round-score
  [line]
  (let [[left right] (map keyword (string/split line #" "))
        opponent-move (moves-by-left-name left)
        result (results-by-name right)
        my-move (case result
                  :draw opponent-move
                  :win (defeats opponent-move)
                  :loss (defeated-by opponent-move))]
    (+ (-> moves my-move :score)
       (-> results result :score))))

(defn part2
  []
  (with-open [rdr (io/reader (io/resource "day2.txt"))]
    (->> (line-seq rdr)
         (map get-correct-round-score)
         (reduce +))))

(comment
  (def opponent-move :rock)
  (def my-move :paper)
  (let [opponent-move :scissors
        my-move :scissors
        result (cond
                 (= my-move opponent-move) :draw
                 (= (-> moves my-move :defeats) opponent-move) :win
                 (= (-> moves opponent-move :defeats) my-move) :loss)]
    (+ (-> moves my-move :score)
       (-> results result :score)))

  (def line "A Y")
  (def moves-by-left-name
    (into {}
          (map (fn [[move {:keys [left-name]}]] [left-name move]))
          moves))
  (def moves-by-right-name
    (into {}
          (map (fn [[move {:keys [right-name]}]] [right-name move]))
          moves))
  (let [moves-by-left-name (into {}
                                 (map (fn [[move {:keys [left-name]}]] [left-name move]))
                                 moves)
        moves-by-right-name (into {}
                                  (map (fn [[move {:keys [right-name]}]] [right-name move]))
                                  moves)
        [left right] (map keyword (string/split line #" "))
        my-move (moves-by-right-name right)
        opponent-move (moves-by-left-name left)
        result (cond
                 (= my-move opponent-move) :draw
                 (= (-> moves my-move :defeats) opponent-move) :win
                 (= (-> moves opponent-move :defeats) my-move) :loss)]
    (+ (-> moves my-move :score)
       (-> results result :score)))
  (get-correct-round-score "A Y"))