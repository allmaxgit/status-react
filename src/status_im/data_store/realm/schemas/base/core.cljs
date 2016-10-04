(ns status-im.data-store.realm.schemas.base.core
  (:require [status-im.data-store.realm.schemas.base.account :as account]
            [status-im.data-store.realm.schemas.base.kv-store :as kv-store]
            [status-im.data-store.realm.schemas.base.migrations.core :as migrations]))

(def schema {:schema [account/schema
                      kv-store/schema]
             :schemaVersion 1
             :migration migrations/migrate})