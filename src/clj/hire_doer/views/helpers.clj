(ns hire-doer.views.helpers)

(defmacro emit-str [& forms]
  `(str (do ~@forms)))

(defmacro get-item [item & fields]
  (let [mp# (apply hash-map (flatten (map #(vector (keyword %) %) fields)))]
    `(let [{:keys [~@fields]} ~item]
       ~mp#)))