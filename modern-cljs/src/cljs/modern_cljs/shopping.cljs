(ns modern-cljs.shopping
  (:require-macros [hiccups.core :as h])
  (:require [domina :as dom]
            [domina.events :as ev]
            [hiccups.runtime :as hiccupsrt]
            [shoreleave.remotes.http-rpc :refer [remote-callback]]
            [cljs.reader :refer [read-string]]))

(defn calculate []
  (let [quantity (read-string (dom/value (dom/by-id "quantity")))
        price (read-string (dom/value (dom/by-id "price")))
        tax (read-string (dom/value (dom/by-id "tax")))
        discount (read-string (dom/value (dom/by-id "discount")))]
    (remote-callback :get-msg [quantity]
                     #(dom/append! (dom/by-id "more") (h/html [:div %])))

    (remote-callback :calculate
                     [quantity price tax discount]
                     #(dom/set-value! (dom/by-id "total") (.toFixed % 2)))))

(defn ^:export init []
  (if (and js/document
           (.-getElementById js/document))
    (ev/listen! (dom/by-id "calc") :click calculate)))