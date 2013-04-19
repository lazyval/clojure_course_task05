(ns hire-doer.main
  (:require [helpers.util :as util]
            [helpers.components :as comps]
            [enfocus.core :as ef]
            [clojure.browser.repl :as repl])
  (:require-macros [enfocus.macros :as em])
  (:use [jayq.core :only [$ css inner]])
  (:use-macros [helpers.macros :only [with-user]])) 

;; (repl/connect "http://localhost:9000/repl")

(declare redraw-page)

(def *user* nil)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Snippets
;;

(em/defsnippet task-item "/html/fragments.html" [:#task-item]
  [{:keys [header content thedate phone id]}]
  [:#header] (em/content header)
  [:#content] (em/content content)
  [:#date] (em/content thedate)
  [:#phone] (em/content phone)
  [:#header-link] (em/set-attr :onclick (str "hire_doer.main.try_show_task_details(" id ");")))

(em/defsnippet task-details "/html/fragments.html" [:#task-details]
  [{:keys [id header content phone thedate]}]
  [:#header] (em/content header)
  [:#date] (em/content thedate)
  [:#phone] (em/content phone)
  [:#content] (em/content content))

(em/defsnippet my-task-item "/html/fragments.html" [:#task-item]
  [{:keys [header content phone thedate id]}]
  [:#header] (em/content header)
  [:#content] (em/content content)
  [:#date] (em/content thedate)
  [:#phone] (em/content phone)
  [:#header-link] (em/set-attr :onclick (str "hire_doer.main.try_show_my_task_details(" id ");")))

(em/defsnippet my-task-details "/html/fragments.html" [:#my-task-details]
  [{:keys [id header content phone thedate]}]
  [:#header] (em/content header)
  [:#date] (em/content thedate)
  [:#phone] (em/content phone)
  [:#content] (em/content content)
  [:#delete-task] (em/set-attr :onclick (str "hire_doer.main.try_delete_task(" id ");"))
  [:#edit-task] (em/set-attr :onclick (str "hire_doer.main.try_show_update_task(" id ");")))

(em/defsnippet create-task-form "/html/fragments.html" [:#create-task]
  [])

(em/defsnippet update-task-form "/html/fragments.html" [:#update-task]
  [{:keys [id header content phone thedate]}]
  [:#header] (em/set-attr :value header)
  [:#phone] (em/set-attr :value phone)
  [:#content] (em/content content)
  [:#close-task] (em/set-attr :onclick (str "hire_doer.main.try_show_my_task_details(" id ");"))
  [:#edit-task] (em/set-attr :onclick (str "hire_doer.main.try_update_task(" id ");")))


(em/defsnippet login-link "/html/fragments.html" [:#login-link]
  [])

(em/defsnippet logged-links "/html/fragments.html" [:#logged-links]
  []
  [:#my-tasks] (em/set-attr :onclick (str "hire_doer.main.try_show_my_task_list(" (:id *user*) ");")))

(em/defsnippet page-header-inner "/html/fragments.html" [:#page-header-inner]
  []
  [:#header-links] (em/content (if (nil? *user*)
                                 (login-link)
                                 (logged-links))))

(em/defsnippet grey-content-inner "/html/fragments.html" [:#grey-content-inner]
  [])

(em/defsnippet task-items-wrapper "/html/fragments.html" [:#task-items-wrapper]
  [content]
  [:#task-items-wrapper] (em/append content))

(em/defsnippet my-task-items-wrapper "/html/fragments.html" [:#my-task-items-wrapper]
  [content]
  [:#my-task-items-wrapper] (em/append content))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Show snippets
;;

(defn full-page [args]
  (em/at js/document
         ["#page-header"] (em/content (or (:header args)
                                          (page-header-inner)))
         ["#grey-content"] (em/content (or (:grey-content args)
                                           (grey-content-inner)))
         ["#task-item-container"] (em/content (:container args))))

(defn ^:export show-task-list [data]
  (full-page {:container (task-items-wrapper
                          (doall (map task-item data)))}))

(defn ^:export show-my-task-list [data]
  (full-page {:container (my-task-items-wrapper
                          (doall (map my-task-item data)))}))

(defn ^:export try-show-my-task-list [id]
  (util/get-data (str "/task-list/" id) show-my-task-list))

(defn ^:export show-update-task [task]
  (full-page {:container (update-task-form task)})
  (js/eval "$('#date').datepicker();$('#date').datepicker('option','dateFormat','dd-mm-yy');")
  (.val ($ "#date") (:thedate task)))

(defn ^:export try-show-update-task [id]
  (util/get-data (str "/task/" id)
                 show-update-task))

(defn ^:export show-create-task []
  (with-user *user*
    {:auth (do (full-page {:container (create-task-form)
                           :grey-content ""})
               (js/eval "$('#date').datepicker();$('#date').datepicker('option','dateFormat','dd-mm-yy');"))
     ;; (.datepicker ($ "#date"))
     ;; (.datepicker ($ "#date") "option" "dateFormat" "dd-mm-yy"))
     :no-auth (hire-doer.user/show-login-dialog)
     }))


(defn ^:export show-task-details [data]
  (full-page {:container (task-details data)}))

(defn ^:export show-my-task-details [data]
  (full-page {:container (my-task-details data)}))


(defn ^:export try-show-task-details [id]
  (util/get-data (str "/task/" id)
                 show-task-details))

(defn ^:export try-show-my-task-details [id]
  (util/get-data (str "/task/" id)
                 show-my-task-details))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Actions
;;


(defn ^:export delete-task [id]
  (util/post-data (str "/task/delete/" id)
                  #(redraw-page "my-task-list"))
  (comps/hide-dialog))


(defn ^:export try-delete-task [id]
  (comps/show-ok-cancel-dialog "Do you really want to delete this task?" (str "hire_doer.main.delete_task(" id ");")))


(defn ^:export create-task [data]
  (redraw-page))

(defn ^:export try-create-task [id]
  (let [header (util/get-element-value :#header)
        content (util/get-element-value :#content)
        phone (util/get-element-value :#phone)
        date (util/get-element-value :#date)]
    (util/post-data "/task/create"
                    create-task
                    {:header header, :content content,
                     :phone phone, :thedate date})))

(defn ^:export update-task [data]
  (redraw-page))

(defn ^:export try-update-task [id]
  (let [header (util/get-element-value :#header)
        content (util/get-element-value :#content)
        phone (util/get-element-value :#phone)
        date (util/get-element-value :#date)]
    (util/post-data (str "/task/update/" id)
                    update-task
                    {:header header, :content content,
                     :phone phone, :thedate date})))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Entry point
;;

(defn ^:export redraw-page [& [page-name]]
  (cond 
    (= "my-task-list" page-name) (try-show-my-task-list (:id *user*))
    :else (util/get-data "/task-list" show-task-list)))


(set! (.-onload js/window) redraw-page) 


(defn ^:export testing-function [n]
  (even? n))