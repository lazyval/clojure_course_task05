(ns hire-doer.models.user
  (:require [hire-doer.db :as db]
            [noir.util.crypt :as crypt])
  (:use [korma db core]
        hire-doer.models.helpers))

(def ^:dynamic *salt* "$2a$10$W/C5O9VVyOTrm/nf6uxuU.")

(defentity user)

(defn- user-exists?
  "Checks whether the user exists by his login."
  [login]
  (not (empty? (select user (where {:login login})))))

(defn- prepare-user
  "Removes from the user map the :password_hash.
   Converts the :admin field from tiny int to boolean."
  [user]
  (if-not (empty? user)
    (-> user
        (dissoc :password_hash)
        (assoc :admin (= 1 (:admin user))))
    nil))

(defn create-user
  "Creates a user and returns his ID like this: {:GENERATED_KEY 3}.
   If the user with the same login already exists -- returns nil."
  [login password admin?]
  (if (user-exists? login)
    nil
    (insert user
            (values {:login login
                     :password_hash (crypt/encrypt *salt* password)
                     :admin admin?}))))

(defn find-user
  "Finds the user by the specified login and password."
  [login password]
  (let [user (first
              (select user
                      (where {:login login
                              :password_hash (crypt/encrypt *salt* password)})))]
    (prepare-user user)))

(defn find-user-by-id
  "Finds the user by his id."
  [id]
  (let [user (first
              (select user
                      (where {:id id})))]
    (prepare-user user)))

(defn update-user
  "Updates the user password and role.
   Returns true if the user was updated and false otherwise."
  [id password admin?]
  (let [user (find-user-by-id id)]
    (if (empty? user)
      false
      (do
        (update user
                (where {:id id})
                (set-fields {:password_hash (crypt/encrypt *salt* password)
                             :admin admin?}))
        true))))

(defn delete-user
  "Deletes the user by his id"
  [id]
  (delete user (where {:id id})))
