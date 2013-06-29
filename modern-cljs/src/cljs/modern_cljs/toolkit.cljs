(ns modern-cljs.toolkit
  (:require-macros [hiccups.core :as h])
  (:require [domina :as dom]
            [domina.events :as ev]
            [domina.xpath :refer [xpath]]
            [hiccups.runtime :as hiccupsrt]
            [shoreleave.remotes.http-rpc :refer [remote-callback]]
            [cljs.reader :refer [read-string]]))

(defn make-field [name v]
  (condp = v
    :number (list [:td [:label name]]
                  [:td [:input {:type :number
                                :name name
                                :id name
                                :value 1
                                :min 0}]])

    :string (list [:td [:label name]]
                  [:td [:input {:type :text
                                :name name
                                :id name
                                :value "" }]]))
  )

(defn make-editor [m]
  (h/html [:div {:id :editor}
           [:table (for [[n v] m] [:tr (make-field n v)])]]))

(defn test [m]
  (for [[n v] m]
    (str  v n)))

(defn edit-type []
  (let [type (dom/value (dom/by-id "type-selector"))]
    (remote-callback :get-for-type [(keyword type)]
                     #(dom/swap-content! (dom/by-id "editor") (make-editor %)))))

(defn ^:export init []
  (when (and js/document
           (.-getElementById js/document))
    (remote-callback :get-types []
                     (fn [types]
                       (dom/destroy! (dom/by-id "loading"))
                       (doseq [t types]
                         (dom/append!
                          (dom/by-id "type-selector")
                          (h/html [:option {:value t} t])
                          ))))
    (ev/listen! (dom/by-id "edit") :click edit-type)))