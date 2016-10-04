(ns status-im.data-store.realm.schemas.account.migrations.v1
  (:require [taoensso.timbre :as log]))

(defn migrate [old-db new-db]
  (log/debug "Migrating to version 1" old-db new-db))