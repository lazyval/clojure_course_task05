(ns browser.main
  (:require [helpers.util :as util]
            [helpers.components :as comps]
            [enfocus.core :as ef]
            )
  (:require-macros [enfocus.macros :as em])
  (:use [jayq.core :only [$ css inner]]))

(em/defsnippet commit "html/fragments.html" [:#commit]
    [{sha :sha message :message commit :commit}]
    [:#header] (em/content sha)
    [:.label]   (em/content commit)
    [:#content](em/content message))


(defn render-commits [response]
    (let [_ (.log js/console "making request")
          json (js->clj response :keywordize-keys true)
          commits (map commit json)
          _ (.log js/console (:sha json))]
          (em/at js/document
            ["#repo"] (em/content commit nil)
                 )))

(defn ^:export try-update-commits [userrepo]
    (let [;userrepo (.val ($ "#user-and-repo"))
          userrepo "clojure/clojure"
          url (str "https://api.github.com/repos/" userrepo "/commits")]
    (do
      (.empty ($ "#repo"))
      ;(util/get-data url render-commits))))
      (render-commits "[{sha: 'some sha', message: 'some message', commit: []}]"))))

;(defn ^:export open-repo []
;   (let [input (-> (get-input)
;                (.val)
;                (clojure.string/split #"/"))
;         user (first input)
;         name  (second input)]
;   (.log js/console ("repo" (get-repo-placeholder)))))
;   ;(.repo (get-repo-placeholder) (util/make-js-map {:user user :name name}))))
