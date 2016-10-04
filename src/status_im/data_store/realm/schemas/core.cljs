(ns status-im.data-store.realm.schemas.core
  (:require [taoensso.timbre :as log]))


(defn migrate [old-db new-db versions]
  (log/debug "MIGRATING !!!!!" old-db new-db)
  (let [old-version (.-schemaVersion old-db)
        new-version (.-schemaVersion new-db)
        migrations (->> (range (inc old-version) (inc new-version))
                        (select-keys versions)
                        (into (sorted-map))
                        vals)
        first-migration (first migrations)
        rest-migrations (rest migrations)]
    (log/debug "Migrate: " old-version new-version)
    (when first-migration
      ;; do first migration with old-db
      (first-migration old-db new-db)
      ;; proceed remaining migrations
      (doseq [migration rest-migrations]
        (migration new-db new-db)))))