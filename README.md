# Использование

* 'http://localhost:8080/data/shorten' - шлюз для POST-запросов. В параметра fullUrl указывать нужный URL. Для валидного URL возвращается JSON-объект с сокращённым URL в параметре id. Иначе - ошибка.
* 'http://localhost:8080/data/expand/{shortUrl}' - Если заменить '{shortUrl}' на сокращённый URL, то вернётся JSON объект с полным URL в параметре fullUrl.
* 'http://localhost:8080/{shortUrl}' - редирект на привязаннуй к '{shortUrl}' адрес.