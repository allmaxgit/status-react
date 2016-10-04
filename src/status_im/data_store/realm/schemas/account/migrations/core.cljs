(ns status-im.data-store.realm.schemas.account.migrations.core
  (:require [status-im.data-store.realm.schemas.account.migrations.v1 :as v1]
            [status-im.data-store.realm.schemas.account.migrations.v2 :as v2]
            [status-im.data-store.realm.schemas.account.migrations.v3 :as v3]
            [status-im.data-store.realm.schemas.core :as schemas]
            [taoensso.timbre :as log]))

(def versions
  {1 v1/migrate
   2 v2/migrate
   3 v3/migrate})

(defn migrate [old-db new-db]
  (schemas/migrate old-db new-db versions))