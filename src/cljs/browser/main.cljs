(ns browser.main
  (:require [helpers.util :as util]
            [helpers.components :as comps]
            [enfocus.core :as ef]
            )
  (:require-macros [enfocus.macros :as em])
  (:use [jayq.core :only [$ css inner]]))

(em/defsnippet commit "html/fragments.html" [:.commit]
    [{sha "sha" commit "commit" message "message"}]

    [:.sha]      (em/content (str "in: " sha))
    [:.date]     (em/content (-> commit "author" "date"))
    [:.message]  (em/content ("message" commit)))


(defn render-commits [response]
    (let [json (js->clj (JSON/parse response) true)
          commits (map commit json)]
          (em/at js/document
            ["#repo"] (em/content commits))))

(defn ^:export try-update-commits [userrepo]
    (let [userrepo (.val ($ "#user-and-repo"))
          url (str "https://api.github.com/repos/" userrepo "/commits")]
    (do
      (.empty ($ "#repo"))
      (util/get-data url render-commits))))

;(defn ^:export open-repo []
;   (let [input (-> (get-input)
;                (.val)
;                (clojure.string/split #"/"))
;         user (first input)
;         name  (second input)]
;   (.log js/console ("repo" (get-repo-placeholder)))))
;   ;(.repo (get-repo-placeholder) (util/make-js-map {:user user :name name}))))
