(ns status-im.data-store.realm.schemas.base.kv-store
  (:require [taoensso.timbre :as log]))

(def schema {:name       :kv-store
             :primaryKey :key
             :properties {:key   "string"
                          :value "string"}})
