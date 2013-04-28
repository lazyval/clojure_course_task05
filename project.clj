(defproject browser "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [lib-noir "0.4.9"]
                 [enfocus "1.0.1"]
                 [jayq "2.3.0"]]
  :plugins [[lein-ring "0.8.3"]
            [lein-cljsbuild "0.3.0"]]
  :ring {:handler browser.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}}

  :aot []
  :source-paths ["src/clj" "src/cljs"]

  :cljsbuild
  {:builds
   [
    {:source-paths ["src/cljs"],
     :id "main",
     :compiler
     {:pretty-print true,
      :output-to "resources/public/js/main.js",
      :warnings true,
      :externs ["externs/jquery-1.9.js"],
;      :optimizations :advanced,
      :optimizations :whitespace,
      :print-input-delimiter false}}
    ],
   }
  :war {:name "browser.war"})


