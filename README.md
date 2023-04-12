## Микросервисы
* currency rate 
микросервис - просто надстройка над API Центрального банка России. У него существует единственный эндпоинт `/rates/{charCode}`, который возвращает в формате JSON данные об валюте по заданному буквенному коду валюты.
* account processor 
микросервис отвечающий за обслуживание счётов пользователей. Для использования требуется Access Token, полученный у микросервиса `auth-server`.
* account history
микросервис хранящий историю операций пользователя. События приходят через RabbitMQ.

## Запуск
Убедитесь в валидности `docker-compose.yml` вызвав команду `docker compose convert`
Запустив через команду `docker compose up --build -d`, проверяйте, чтобы сервисы не упали, не дождавшись зависимый сервис.
> Сверяйтесь с помощью опции `depends_on` в `docker-compose.yml`

## Authorization server
Access Token легко можно получить с помощью [oidcdebugger](https://oidcdebugger.com/) или через Postman.

Credentials: 
https://github.com/slappidyslap/currency-microservices/blob/80a9d8085d0a4b5830ae6d40a8b0a409df6eac5f/auth-server/src/main/java/kg/musabaev/authserver/SecurityConfig.java#L83-L102
> Клиент `oauth-client` получает токен через [PKCE](https://auth0.com/docs/get-started/authentication-and-authorization-flow/authorization-code-flow-with-proof-key-for-code-exchange-pkce) 

## Полезное
https://docs.sonarqube.org/9.9/analyzing-source-code/ci-integration/jenkins-integration/

https://www.rabbitmq.com/reliability.html

https://habr.com/ru/company/flant/blog/460367/