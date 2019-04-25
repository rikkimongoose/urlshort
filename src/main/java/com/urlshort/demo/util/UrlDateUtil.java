package com.urlshort.demo.util;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;

public class UrlDateUtil {
    public static final Duration EXPIRATION = Duration.ofMinutes(10);

    public static boolean isExpired(LocalDateTime dateTime) {
        return Duration.between(dateTime, LocalDateTime.now()).compareTo(EXPIRATION) > 0;
    }

    public static boolean isUrlValid(String uri){
            final URL url;
            try {
                url = new URL(uri);
            } catch (Exception e1) {
                return false;
            }
            return true;
    }
}
