package com.urlshort.demo.util;

import org.apache.commons.lang3.RandomStringUtils;

public class UrlCodeUtil {
    public static final int DEFAULT_SHORT_URL_LENGTH = 5;

    public static String generateCode(int maxLength) {
        return RandomStringUtils.randomAlphanumeric(maxLength);
    }
}
