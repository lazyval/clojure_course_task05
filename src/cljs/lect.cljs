(ns lect
  (:require [enfocus.core :as ef]
            [clojure.browser.repl :as repl])
  (:require-macros [enfocus.macros :as em])
  (:use [jayq.core :only [$ css inner]]))

;; (repl/connect "http://localhost:9000/repl")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 9

;; (defn start []
;;   (let [dlg ($ "#some-dialog")]
;;     (.show dlg))
;;   )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 8

;; (em/defsnippet fragm3 "/html/lect.html" [:#fragm3]
;;   []
;;   [:#fragm3] (em/set-attr :style ""))

;; (defn ^:export show-data []
;;   (let [data (em/from js/document
;;                       :myvalue [:#mytext] (em/get-prop :value))]
;;     (js/alert (str data))))

;; (defn start []
;;   (em/at js/document
;;          [:#insert-here] (em/content (fragm3))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 7

;; (em/defsnippet fragm2 "/html/lect.html" [:#fragm2]
;;   [{:keys [header content]}]
;;   [:h2] (em/do-> (em/set-attr :style "color:blue;font-style:bold")
;;                  (em/content header))
;;   [:p] (em/do-> (em/add-class "myclass")
;;                 (em/content content)))

;; (em/defaction fade-demo [] 
;;   [:#insert-here] (em/do-> (em/content (fragm2 {:header "The header",
;;                                                 :content "The content"}))
;;                            (em/fade-in 1500)))

;; (defn start []
;;   (em/at js/document
;;          [:#insert-here] (fade-demo))
;;   )


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 6


;; (em/defsnippet table-row "/html/lect.html" [:#tbl-row]
;;   [])

;; (em/defsnippet table "/html/lect.html" [:#mytable]
;;   []
;;   [:#mytable] (em/do-> (em/set-attr :style "")
;;                        (em/append (table-row))))

;; (defn start []
;;   (em/at js/document
;;          [:#insert-here] (em/content (table))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 5

;; (em/defsnippet fragm2 "/html/lect.html" [:#fragm2]
;;   [{:keys [header content]}]
;;   [:h2] (em/do-> (em/set-attr :style "color:blue;font-style:bold")
;;                  (em/content header))
;;   [:p] (em/do-> (em/add-class "myclass")
;;                 (em/content content)))

;; (defn start []
;;   (em/at js/document
;;          [:#insert-here] (em/content (fragm2 {:header "My header"
;;                                               :content "My content"}))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 4

;; (em/defsnippet fragm1 "/html/lect.html" [:#fragm1]
;;   [param]
;;   [:#fragm1] (em/content param))

;; (def lst ["abc" "def" "ghi" "jkl"])

;; (defn start []
;;   (em/at js/document
;;          [:#insert-here] (em/content (map fragm1 lst))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 3

;; (em/defsnippet fragm1 "/html/lect.html" [:#fragm1]
;;   [param]
;;   [:#fragm1] (em/content param))

;; (defn start []
;;   (em/at js/document
;;          [:#insert-here] (em/content (fragm1 "Hello 1!")
;;                                      (fragm1 "Hello 2!"))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 2

;; (defn start [] 
;;   (em/at js/document
;;     ["body"] (em/content "Hello world!")))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 1

;; (defn start [] (js/alert "!!!"))

;; (set! (.-onload js/window) start)

