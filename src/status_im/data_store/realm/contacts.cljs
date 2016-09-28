(ns status-im.data-store.realm.contacts
  (:require [status-im.data-store.realm.core :as realm]))

(defn get-all
  []
  (-> (realm/get-all @realm/account-realm :contact)
      (realm/sorted :name :asc)))

(defn get-all-as-list
  []
  (-> (get-all)
      realm/realm-collection->list))

(defn get-by-id
  [whisper-identity]
  (realm/get-one-by-field @realm/account-realm :contact :whisper-identity whisper-identity))

(defn save
  [contact update?]
  (realm/save @realm/account-realm :contact contact update?))
