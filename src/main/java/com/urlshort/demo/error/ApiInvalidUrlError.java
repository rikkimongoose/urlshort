package com.urlshort.demo.error;

/**
 * Ошибка - некорректный URL
 */
public class ApiInvalidUrlError extends ApiSubError {
    public ApiInvalidUrlError(String url) {
        super(String.format("URL '%s' is not valid", url));
    }
}
