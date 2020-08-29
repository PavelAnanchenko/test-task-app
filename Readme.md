##Авторизация и аутентификация
Авторизация в данном приложении основана на технологии JwtToken. Серивис получения (генерации) токенов в рамках ТЗ не был
реализован. Подразумевается работа с разнесением логики генерации и авторизации на разные сервисы. В результате чего достигается возможность локальной авторизации на ресурс-сервере. 
Сервис генерации должен поставить клиенту токен в соответствующий следующим критериям:
 -- Header должен содержать поле "alg": "HS256", устанавливающий алгоритм шифрования
 -- Payload data содержит информацию о дате (java.util.Date) создания и дате окончания срока действия
 -- Verify Signature содержит приватный ключ, закодированный Base64 (идентичный gприватный ключ должен находиться на ресурс сервере в application.properties в поле jwt.token.secret)
Для получения доступа к сервису приложения клиент должен разместить полученный от сервиса генерации токен в заголовоке запроса (headers): 
(key) Authorization (value) токен

При поступлении запроса от клиента к сервису будет произведен поиск соответствуюшего заголовка и если такой имеется считано значение токена и его верификация. 
В результате успешной верификации клиент будет ассоциирован с аннонимной учетной записью с правами DEFAULT_USER позвляющими получить доступ ко всем внешним ресурсам приложения.
В противном случае доступ возможен только по адресу "/exit"

Пример токена для доступа к приложению:
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQHlhbmRleC5ydSIsImlhdCI6MTU5ODQ0ODkxOCwiZXhwIjoxNTk5ODA5MjAwfQ.1hpJ63McIlzeg3_QR8j6-lG1y_B-KyqKn7bTAYINIt4
Срок действия: 26.08.2020/11.09.2020
REST API with JWT Authentication and Swagger documentation


###endpoints:
####POST /profiles/set 
Создает запись профиля и присваивает ему id
Request:
принимает json следующей структурой:

{
	“name”: string
	“email”: string
	“age”: int
}

Responses:
в случае успеха возвращает id записи пользователя

status 200
{
	“idUser”: int
}

В случае некорректного email
status 400

{
	“msg”: string
}

В случае если email уже передавался (реализовать через фильтр)
status 403

{
	“msg”: string
}

####GET /profiles/last
Возвращает последний созданный профиль

Responses:
status 200
{
	“id”: int
	“name”: string
	“email”: string
	“age”: int
“created”: timestamp
}

####GET /profiles
Возвращает все созданные профили

Responses:
status 200
[{
	“id”: int
	“name”: string
	“email”: string
	“age”: int
“created”: timestamp
}...]

####GET /profiles/{ID}
Возвращает профиль по его ID

Responses:
status 200
{
	“id”: int
	“name”: string
	“email”: string
	“age”: int
“created”: timestamp
}

status 404 
в случае если запись не найдена
{
	“msg”: string
}

####POST /profiles/get
Возвращает профиль по email

Request:
принимает json следующей структурой:

{
	“email”: string
}

Responses:
status 200
{
	“id”: int
	“name”: string
	“email”: string
	“age”: int
“created”: timestamp
}

status 404 
в случае если запись не найдена

{
	“msg”: string
}

####GET /error/last
Возвращает сообщение последней ошибки

Responses:
status 200
{
	“msg”: string
	“created”: timestamp
}


Не обязательная часть задания:

####GET /exit
Производит закрытия приложение с редиректом на страницу /exit-success (название вариативно) с надписью ‘приложение закрыто’ допускаются и другие варианты информирования о закрытие.
