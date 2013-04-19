(ns hire-doer.db)

(def default-conn {:classname "com.mysql.jdbc.Driver"
                   :subprotocol "mysql"
                   :user "hiredoer"
                   :password "hiredoer"
                   :subname "//127.0.0.1:3306/hiredoer?useUnicode=true&characterEncoding=utf8"
                   :delimiters "`"})

;; (def default-conn {:classname "com.mysql.jdbc.Driver"
;;                    :subprotocol "mysql"
;;                    :user "hiredoer"
;;                    :password "hiredoer"
;;                    :subname "//127.0.0.1:3639/hiredoer?useUnicode=true&characterEncoding=utf8"
;;                    :delimiters "`"})
