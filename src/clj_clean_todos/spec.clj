(ns clj-clean-todos.spec
  (:require [clojure.spec.alpha :as s]))

;; Entity

(s/def ::id pos-int?)
(s/def ::title string?)
(s/def ::todo (s/keys :req-un [::id ::title]))
(s/def ::todos (s/coll-of ::todo))
