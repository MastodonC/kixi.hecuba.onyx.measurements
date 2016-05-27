(ns kixi.hecuba.onyx.measurements
  (:require [com.stuartsierra.component :as component]
            [taoensso.timbre :as timbre]
            [kixi.hecuba.onyx.jobs.measurements]
            [kixi.hecuba.onyx.components.onyx-job :refer [new-onyx-job]]))

(defn new-system
  []
  (let [mode :dev]
    (component/system-map
     :onyx-events   (new-onyx-job mode 'kixi.hecuba.onyx.jobs.measurements/build-job))))
