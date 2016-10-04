(ns status-im.data-store.realm.schemas.base.migrations.core
  (:require [status-im.data-store.realm.schemas.base.migrations.v1 :as v1]
            [status-im.data-store.realm.schemas.core :as schemas]
            [taoensso.timbre :as log]))

(def versions
  {1 v1/migrate})

(defn migrate [old-db new-db]
  (schemas/migrate old-db new-db versions))