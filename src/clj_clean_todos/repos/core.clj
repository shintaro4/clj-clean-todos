(ns clj-clean-todos.repos.core)

(defprotocol IRepository
  (find-all [this])
  (store [this title])
  (remove-by-id [this id])
  (remove-all [this]))
