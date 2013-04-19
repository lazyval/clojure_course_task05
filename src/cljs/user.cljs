(ns hire-doer.user
  (:require [helpers.util :as util]
            [helpers.components :as comps]
            [enfocus.core :as ef]
            [hire-doer.main :as main])
  (:require-macros [enfocus.macros :as em])
  (:use [jayq.core :only [$ css inner]])) 


(em/defsnippet login-fragment "/html/fragments.html" [:#login-dlg]
  [])

(defn ^:export show-login-dialog []
  (comps/show-dialog {:header "Login dialog",
                      :body (login-fragment),
                      :footer (comps/ok-cancel-buttons "hire_doer.user.try_login()"),
                      :width 315}))

(defn process-login [data]
  (set! main/*user* data)
  (main/redraw-page))

(defn ^:export try-login []
  (let [login (util/get-element-value :#login)
        password (util/get-element-value :#password)]
    (util/post-data "/user/login"
                    process-login
                    {:login login,
                     :password password}))
  (comps/hide-dialog))

(defn ^:export try-logout []
  (util/post-data "/user/logout"
                  main/redraw-page)
  (set! main/*user* nil)
  (comps/hide-dialog))