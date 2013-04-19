(ns hire-doer.models.task
  (:require [hire-doer.db :as db])
  (:use [korma db core]
        hire-doer.models.helpers))

(defentity task)

(def pint (fn [n] (Integer/parseInt n)))

(defn item-details [id]
  (first (select task (where {:id (pint id)}))))

(defn select-recent-tasks []
  (select task (limit 10) (order :id :DESC)))

(defn select-user-tasks [user_id]
  (select task
          (where {:user_id user_id})
          (order :id :DESC)))

(defn create-item [item]
  (insert task (values item)))

(defn update-item [item]
  (update task
          (set-fields item)
          (where {:id (:id item)})))

(defn delete-item [id]
  (delete task (where {:id (pint id)})))