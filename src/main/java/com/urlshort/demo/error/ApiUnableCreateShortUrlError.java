package com.urlshort.demo.error;

/**
 * Ошибка - не удалось создать короткий URL
 */
public class ApiUnableCreateShortUrlError extends ApiSubError{
    public ApiUnableCreateShortUrlError(String id) {
        super(String.format("Unable to create a short URL for '%s'", id));
    }
}
