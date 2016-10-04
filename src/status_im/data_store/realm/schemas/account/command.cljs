(ns status-im.data-store.realm.schemas.account.command
  (:require [taoensso.timbre :as log]))

(def schema {:name       :command
             :primaryKey :chat-id
             :properties {:chat-id "string"
                          :file    "string"}})