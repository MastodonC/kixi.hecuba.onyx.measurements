(ns kixi.hecuba.onyx.jobs.measurements-connector
  (:require [cheshire.core :as json]
            [clj-http.client :as client]
            [environ.core :refer [env]]))

(defn push-payload-to-hecuba
  "Create the http post request for measurements
  uploads"
  [json-payload entity-id device-id]
  (try (client/post
        (str (env :hecuba-endpoint) "entities/" entity-id "/devices/" device-id "/measurements/")
        {:basic-auth [(env :hecuba-username) (env :hecuba-password)]
         :body (json/generate-string json-payload)
         :headers {"X-Api-Version" "2"}
         :content-type :json
         :socket-timeout 20000
         :conn-timeout 20000
         :accept "application/json"})
       (catch Exception e (doall (str "Caught Exception " (.getMessage e))

                                 (comment (log/error e "> There was an error during the upload to entity " entity-id))))
       (finally {:message "push-payload-to-hecuba complete."})))


;; username, password and api endpoint for the hecuba are read from environment variables
;; export HECUBA_USERNAME=youremail@email.thing and
;; export HECUBA_PASSWORD=mypassword
;; export HECUBA_API_ENDPOINT=http://localhost:8010/4/

(defn get-data [fn-data]
  (let [measurements (get-in fn-data [:message :measurements])
        degree-day (get-in fn-data [:message :degree-day])
        entity-id (get-in fn-data [:message :kafka-payload :entity-id])
        device-id (get-in fn-data [:message :kafka-payload :device-id])]
    (push-payload-to-hecuba measurements entity-id device-id)
    (push-payload-to-hecuba degree-day entity-id device-id)))
