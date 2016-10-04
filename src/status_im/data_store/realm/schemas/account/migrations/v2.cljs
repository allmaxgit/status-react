(ns status-im.data-store.realm.schemas.account.migrations.v2
  (:require [taoensso.timbre :as log]))




(defn migrate [old-db new-db]
  (log/debug "Migrating to version 2")
  (let [old-objects (.objects old-db "message")
        new-objects (.objects new-db "message")]
    ;; rename "message-id" to "id"
    (doseq [i (range (.-length old-objects))]
      (aset (aget new-objects i)
            "content1"
            (aget old-objects "content")))
    (log/debug new-objects)))