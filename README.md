# ClojureScript Example Application

Позволяет создавать, редактировать, просматривать и удалять задания. Реализована аутентификация.


## Предварительные требования

* Leiningent 2.0 или выше (http://leiningen.org)
* MySQL
* PhantomJS для тестирования (http://phantomjs.org)

## Запуск

Прежде -- настройте БД. Создайте базу hiredoer с пользователем hiredoer и паролем hiredoer.

Импортируйте в базу таблицы из дампа SQL/database.sql.

Cкомпилируйте приложение.

    lein cljsbuild once

Запустите его: 

    lein ring server

Откройте приложение в браузере: http://localhost:3000/html/main.html

В БД настроены два пользователя: user/user и root/root.

## License

Copyright © 2013 BSD
