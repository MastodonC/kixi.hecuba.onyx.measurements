(ns kixi.hecuba.onyx.jobs.measurements-connector
  (:require [cheshire.core :as json]
            [clj-http.client :as client]
            [environ.core :refer [env]]
            [schema.core :as s]))

(defn get-data [fn-data]
  ;; map of data passed in from the workflow here.
  ;; TODO - needs to take the measurements and save them via Hecuba API

  (println (str "k.h.o.j.hf - data - " fn-data)))


(s/defn save-measurements
  ([task-name :- s/Keyword task-opts]
   {:task {:task-map (merge {:onyx/name task-name
                             :onyx/type :function
                             :onyx/fn :kixi.hecuba.onyx.jobs.measurements-connector/get-data}
                            task-opts)}}))
