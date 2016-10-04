(ns status-im.data-store.realm.schemas.account.migrations.v3
  (:require [taoensso.timbre :as log]))




(defn migrate [old-db new-db]
  (log/debug "Migrating to version 3")
  (let [old-objects (.objects old-db "message")
        new-objects (.objects new-db "message")]
    ;; rename "message-id" to "id"
    (doseq [i (range (.-length old-objects))]
      (aset (aget new-objects i)
            "content2"
            (-> (aget old-objects i)
                (aget "content1"))))
    (log/debug new-objects)))