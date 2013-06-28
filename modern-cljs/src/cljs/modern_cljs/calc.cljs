(ns modern-cljs.calc
  (:require [domina :as dom]
            [domina.events :as ev]))

(defn calculate []
  (let [email    (dom/by-id "email")
        password (dom/by-id "password")
        v (-> (+ (count email)
                 (count password))
              (.toFixed 2))]
    (js/alert (str "ass!" v))
    (dom/set-value! (dom/by-id "result-spot") v)
    ))

(defn ^:export init []
  (when (and js/document (.-getElementById js/document))
   ; (js/alert "loading!")
    (ev/listen! (dom/by-id "calc-btn") :click calculate)))
