(ns browser.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [noir.util.middleware :as noir]
            [noir.session :as session]
            [noir.response :as resp]))

(defroutes app-routes
  (GET "/" [] (resp/redirect "/main.html"))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> [(handler/site app-routes)]
      noir/app-handler
      noir/war-handler))

