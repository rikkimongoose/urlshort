package com.urlshort.demo.service;

import com.urlshort.demo.model.Url;

/**
 * Сервис для работы с сокращёнными URL.
 */
public interface UrlService {
    /**
     * Получить URL по сокращённой версии.
     * @param index - сокращённая версия
     * @return URL, если он есть, либо null.
     */
    Url getByIndex(String index);

    /**
     * Получить сокращённую версию URL. Если объект уже существует, дата последнего создания будет обновлена.
     *
     * @param fullUrl - полная версия
     * @return Объект с сокращённой и полной версиями URL.
     */
    Url add(String fullUrl);
}
