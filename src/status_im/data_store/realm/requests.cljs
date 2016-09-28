(ns status-im.data-store.realm.requests
  (:require [status-im.data-store.realm.core :as realm]))

(defn get-all
  []
  (realm/get-all @realm/account-realm :request))

(defn get-all-as-list
  []
  (-> (get-all)
      realm/realm-collection->list))

(defn get-by-chat-id
  [chat-id]
  (realm/get-one-by-field @realm/account-realm :request :chat-id chat-id))

(defn save
  [request]
  (realm/save @realm/account-realm :request request true))

(defn save-all
  [requests]
  (realm/save-all @realm/account-realm :request requests true))
