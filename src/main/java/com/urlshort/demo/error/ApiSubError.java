package com.urlshort.demo.error;

import org.apache.log4j.Logger;

public abstract class ApiSubError {
    static Logger logger = Logger.getLogger(ApiSubError.class);
    private final String message;

    public ApiSubError(String message){
        this.message = message;
        logger.error(message);
    }

    public String getMessage() {
        return message;
    }
}
