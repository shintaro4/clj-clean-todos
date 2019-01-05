(ns clj-clean-todos.repos.inmemory
  (:require [clj-clean-todos.repos.core :refer [IRepository]]))

(defn- allocate-next-id
  [{:keys [repository]}]
  (-> @repository
      keys
      last
      ((fnil inc 0))))

(defn- ->entity-todo
  [{:keys [id title] :as inmemory-todo}]
  {:id id
   :title title})

(defrecord InMemory [repository]
  IRepository

  (store [{:keys [repository] :as this} title]
    (let [id (allocate-next-id this)
          todo {:id id :title title}]
      (swap! repository assoc id todo)
      todo))

  (find-all [{:keys [repository] :as this}]
    (->> repository
         deref
         vals
         (mapv ->entity-todo)))

  (remove-by-id [{:keys [repository] :as this} id]
    (let [found (contains? @repository id)]
      (swap! repository dissoc id)
      found))

  (remove-all [{:keys [repository] :as this}]
    (reset! repository {})))

(defn create-inmemory
  []
  (let [repository (atom {})]
    (->InMemory repository)))
