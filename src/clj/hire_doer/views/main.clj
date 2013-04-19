(ns hire-doer.views.main
  (:use compojure.core
        hire-doer.views.helpers)
  (:require [compojure.route :as route]
            [noir.session :as session]
            [noir.response :as resp]
            [hire-doer.models.task :as task]
            ))

(defn task-list []
  (emit-str (task/select-recent-tasks)))

(defn my-task-list [id]
  (emit-str (task/select-user-tasks id)))

(defn task-details [id]
  (emit-str (task/item-details id)))

(defn task-create [item]
  (-> item
      (get-item header content thedate phone)
      (assoc :user_id (:id (session/get :user)))
      task/create-item)
  (emit-str "done"))

(defn task-update [item]
  (-> item
      (get-item id header content thedate phone)
      task/update-item)
  (emit-str "done"))

(defn task-delete [id]
  (task/delete-item id)
  (emit-str "done"))

(defroutes app-routes
  (GET "/task-list" [] (task-list))
  (GET "/task-list/:user_id" [user_id] (my-task-list user_id))
  (POST "/task/create" {params :params} (task-create params))
  (POST "/task/update/:id" {params :params} (task-update params))
  (POST "/task/delete/:id" [id] (task-delete id))
  (GET "/task/:id" [id] (task-details id)))
