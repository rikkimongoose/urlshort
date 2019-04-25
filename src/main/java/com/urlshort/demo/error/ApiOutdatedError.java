package com.urlshort.demo.error;

/**
 * Ошибка - короткий URL устарел
 */
public class ApiOutdatedError extends ApiSubError {
    public ApiOutdatedError(String id) {
        super(String.format("URL by code '%s' is outdated.", id));
    }
}
