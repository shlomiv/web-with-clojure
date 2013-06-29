(ns modern-cljs.remotes
  (:require [modern-cljs.core :refer [handler]]
            [compojure.handler :refer [site]]
            [shoreleave.middleware.rpc :refer [defremote wrap-rpc]]))

(defremote calculate [quantity price tax discount]
  (-> (* quantity price)
      (* (inc (/ tax 100)))
      (- discount)))

(defremote get-msg [quantity]
  (str "ordered " quantity " items!"))


(def types (atom {:Cart {:quantity :number
                         :height   :number}
                  :Person {:name :string
                           :age  :number}
                  :Dog    {:name :string
                           :brand :string
                           :age :number
                           :height :number
                           :color :string}
                  :Another {:name :string
                            :where :string
                            :long :number}}))

(def data  (atom {}))

(defremote get-types []
  (keys @types))

(defremote get-for-type [type]
  (or (@types type) {:error "type not found"}))

(defremote save-instance [name type vals]
  (swap! data assoc name {:type type :vals vals}))

(defremote get-instance [name]
  (or (data name) {:error "instance not found"}))

(def app (-> (var handler)
             (wrap-rpc)
             (site)))