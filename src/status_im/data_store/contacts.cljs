(ns status-im.data-store.contacts
  (:require [status-im.data-store.realm.contacts :as data-store]))

(defn get-all
  []
  (data-store/get-all-as-list))

(defn get-by-id
  [id]
  (data-store/get-by-id id))

(defn save
  [{:keys [whisper-identity pending] :as contact}]
  (let [{pending-db :pending
         :as        contact-db} (data-store/get-by-id whisper-identity)
        contact (assoc contact :pending (boolean (if contact-db
                                                   (and pending-db pending)
                                                   pending)))]
    (data-store/save contact (if contact-db true false))))

(defn save-all
  [contacts]
  (mapv save contacts))
