(ns modern-cljs.core
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/test" [] "<button><a>gole</a>ass</button>")
  (GET "/" [] "<p>Hello compojure</p>")
  (route/resources "/")
  (route/not-found "Page not found"))

(def handler
  (handler/site app-routes))
