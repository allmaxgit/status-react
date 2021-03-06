(ns status-im.chat.screen
  (:require-macros [status-im.utils.views :refer [defview]])
  (:require [re-frame.core :refer [subscribe dispatch]]
            [status-im.components.react :refer [view
                                                animated-view
                                                text
                                                icon
                                                modal
                                                touchable-highlight
                                                list-view
                                                list-item]]
            [status-im.components.status-bar :refer [status-bar]]
            [status-im.components.chat-icon.screen :refer [chat-icon-view-action
                                                           chat-icon-view-menu-item]]
            [status-im.chat.styles.screen :as st]
            [status-im.utils.listview :refer [to-datasource-inverted]]
            [status-im.utils.utils :refer [truncate-str]]
            [status-im.utils.datetime :as time]
            [status-im.utils.platform :refer [platform-specific]]
            [status-im.components.invertible-scroll-view :refer [invertible-scroll-view]]
            [status-im.components.toolbar.view :refer [toolbar]]
            [status-im.chat.views.message :refer [chat-message]]
            [status-im.chat.views.datemark :refer [chat-datemark]]
            [status-im.chat.views.response :refer [response-view]]
            [status-im.chat.views.new-message :refer [chat-message-input-view]]
            [status-im.chat.views.staged-commands :refer [staged-commands-view]]
            [status-im.chat.views.actions :refer [actions-view]]
            [status-im.chat.views.bottom-info :refer [bottom-info-view]]
            [status-im.chat.views.toolbar-content :refer [toolbar-content-view]]
            [status-im.chat.views.suggestions :refer [suggestion-container]]
            [status-im.i18n :refer [label label-pluralize]]
            [status-im.components.animation :as anim]
            [status-im.components.sync-state.offline :refer [offline-view]]
            [status-im.constants :refer [content-type-status]]
            [reagent.core :as r]))

(defn contacts-by-identity [contacts]
  (->> contacts
       (map (fn [{:keys [identity] :as contact}]
              [identity contact]))
       (into {})))

(defn add-message-color [{:keys [from] :as message} contact-by-identity]
  (if (= "system" from)
    (assoc message :text-color :#4A5258
                   :background-color :#D3EEEF)
    (let [{:keys [text-color background-color]} (get contact-by-identity from)]
      (assoc message :text-color text-color
                     :background-color background-color))))

(defview chat-icon []
  [chat-id [:chat :chat-id]
   group-chat [:chat :group-chat]
   name [:chat :name]
   color [:chat :color]]
  ;; TODO stub data ('online' property)
  [chat-icon-view-action chat-id group-chat name color true])

(defn typing [member]
  [view st/typing-view
   [view st/typing-background
    [text {:style st/typing-text
           :font  :default}
     (str member " " (label :t/is-typing))]]])

(defn typing-all []
  [view st/typing-all
   ;; TODO stub data
   (for [member ["Geoff" "Justas"]]
     ^{:key member} [typing member])])

(defmulti message-row (fn [{{:keys [type]} :row}] type))

(defmethod message-row :datemark
  [{{:keys [value]} :row}]
  (list-item [chat-datemark value]))

(defmethod message-row :default
  [{:keys [contact-by-identity group-chat messages-count row index]}]
  (let [message (-> row
                    (add-message-color contact-by-identity)
                    (assoc :group-chat group-chat)
                    (assoc :messages-count messages-count)
                    (assoc :index index)
                    (assoc :last-message (= (js/parseInt index) (dec messages-count))))]
    (list-item [chat-message message])))

(defn toolbar-action []
  (let [show-actions (subscribe [:chat-ui-props :show-actions?])]
    (fn []
      (if @show-actions
        [touchable-highlight
         {:on-press #(dispatch [:set-chat-ui-props :show-actions? false])}
         [view st/action
          [icon :up st/up-icon]]]
        [touchable-highlight
         {:on-press #(dispatch [:set-chat-ui-props :show-actions? true])}
         [view st/action
          [chat-icon]]]))))

(defview add-contact-bar []
  [pending-contact? [:chat :pending-contact?]
   chat-id [:get :current-chat-id]]
  (when pending-contact?
    [touchable-highlight
     {:on-press #(dispatch [:add-pending-contact chat-id])}
     [view st/add-contact
      [text {:style st/add-contact-text}
       (label :t/add-to-contacts)]]]))

(defview chat-toolbar []
  [show-actions? [:chat-ui-props :show-actions?]
   accounts [:get :accounts]]
  [view
   [status-bar]
   [toolbar {:hide-nav?      (or (empty? accounts) show-actions?)
             :custom-content [toolbar-content-view]
             :custom-action  [toolbar-action]
             :style          (get-in platform-specific [:component-styles :toolbar])}]
   [add-contact-bar]])
(defn get-intro-status-message [all-messages]
  (let [{:keys [timestamp content-type] :as last-message} (last all-messages)]
    (when (not= content-type content-type-status)
      {:message-id   "intro-status"
       :content-type content-type-status
       :timestamp    (or timestamp (time/now-ms))})))


(defn messages-with-timemarks [all-messages]
  (let [status-message (get-intro-status-message all-messages)
        all-messages   (if status-message
                         (concat all-messages [status-message])
                         all-messages)
        messages       (->> all-messages
                            (sort-by :clock-value >)
                            (map #(assoc % :datemark (time/day-relative (:timestamp %))))
                            (group-by :datemark)
                            (map (fn [[k v]] [v {:type :datemark :value k}]))
                            (flatten))
        remove-last?   (some (fn [{:keys [content-type]}]
                               (= content-type content-type-status))
                             messages)]
    (if remove-last?
      (drop-last messages)
      messages)))

(defview messages-view [group-chat]
  [messages [:chat :messages]
   contacts [:chat :contacts]
   loaded? [:all-messages-loaded?]]
  (let [contacts' (contacts-by-identity contacts)
        messages  (messages-with-timemarks messages)]
    [list-view {:renderRow                 (fn [row _ index]
                                             (message-row {:contact-by-identity contacts'
                                                           :group-chat          group-chat
                                                           :messages-count      (count messages)
                                                           :row                 row
                                                           :index               index}))
                :renderScrollComponent     #(invertible-scroll-view (js->clj %))
                :onEndReached              (when-not loaded? #(dispatch [:load-more-messages]))
                :enableEmptySections       true
                :keyboardShouldPersistTaps true
                :dataSource                (to-datasource-inverted messages)}]))

(defn messages-container-animation-logic
  [{:keys [offset val]}]
  (fn [_]
    (anim/start (anim/spring val {:toValue @offset}))))

(defn messages-container [messages]
  (let [offset          (subscribe [:messages-offset])
        messages-offset (anim/create-value 0)
        context         {:offset offset
                         :val    messages-offset}
        on-update       (messages-container-animation-logic context)]
    (r/create-class
      {:component-did-mount
       on-update
       :component-did-update
       on-update
       :reagent-render
       (fn [messages]
         @offset
         (let [staged-scroll-height (subscribe [:get-chat-staged-commands-scroll-height])]
           [animated-view {:style (st/messages-container @staged-scroll-height messages-offset)}
            messages]))})))

(defn chat []
  (let [group-chat        (subscribe [:chat :group-chat])
        show-actions?     (subscribe [:chat-ui-props :show-actions?])
        show-bottom-info? (subscribe [:chat-ui-props :show-bottom-info?])
        command?          (subscribe [:command?])
        staged-commands   (subscribe [:get-chat-staged-commands])
        layout-height     (subscribe [:get :layout-height])]
    (r/create-class
      {:component-did-mount #(dispatch [:check-autorun])
       :reagent-render
       (fn []
         [view {:style    st/chat-view
                :onLayout (fn [event]
                            (let [height (.. event -nativeEvent -layout -height)]
                              (when (not= height @layout-height)
                                (dispatch [:set-layout-height height]))))}
          [chat-toolbar]
          [messages-container
           [messages-view @group-chat]]
          ;; todo uncomment this
          #_(when @group-chat [typing-all])
          (when (seq @staged-commands)
            [staged-commands-view @staged-commands])
          (when-not @command?
            [suggestion-container])
          [response-view]
          [chat-message-input-view]
          (when @show-actions?
            [actions-view])
          (when @show-bottom-info?
            [bottom-info-view])
          [offline-view {:top (get-in platform-specific [:component-styles :status-bar :default :height])}]])})))
