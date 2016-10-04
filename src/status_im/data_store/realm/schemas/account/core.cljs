(ns status-im.data-store.realm.schemas.account.core
  (:require [status-im.data-store.realm.schemas.account.chat :as chat]
            [status-im.data-store.realm.schemas.account.chat-contact :as chat-contact]
            [status-im.data-store.realm.schemas.account.command :as command]
            [status-im.data-store.realm.schemas.account.contact :as contact]
            [status-im.data-store.realm.schemas.account.discovery :as discovery]
            [status-im.data-store.realm.schemas.account.kv-store :as kv-store]
            [status-im.data-store.realm.schemas.account.message :as message]
            [status-im.data-store.realm.schemas.account.pending-message :as pending-message]
            [status-im.data-store.realm.schemas.account.request :as request]
            [status-im.data-store.realm.schemas.account.tag :as tag]
            [status-im.data-store.realm.schemas.account.user-status :as user-status]
            [status-im.data-store.realm.schemas.account.migrations.core :as migrations]
            [taoensso.timbre :as log]))

; put schemas ordered by version
(def schema {:schema [chat/schema
                        chat-contact/schema
                        command/schema
                        contact/schema
                        discovery/schema
                        kv-store/schema
                        message/schema
                        pending-message/schema
                        request/schema
                        tag/schema
                        user-status/schema
                        ]
             :schemaVersion 1
             :migration migrations/migrate})