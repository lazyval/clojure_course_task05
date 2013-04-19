(ns hire-doer.views.user
  (:use compojure.core
        hire-doer.views.helpers)
  (:require [compojure.route :as route]
            [noir.session :as session]
            [noir.response :as resp]
            [hire-doer.models.user :as user]
            ))

(defn user-login [login password]
  (let [user (user/find-user login password)]
    (if-not (nil? user)
      (session/put! :user user))
    (emit-str user)))

(defn user-logout []
  (session/put! :user nil)
  (emit-str "done"))

(defroutes app-routes
  (POST "/user/login" [login password] (user-login login password))
  (POST "/user/logout" [] (user-logout)))
