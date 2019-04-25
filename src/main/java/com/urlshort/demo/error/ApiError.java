package com.urlshort.demo.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Ошибка выполнения
 */
public class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private final List<ApiSubError> subErrors = new ArrayList<>();

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, ApiSubError subError) {
        this(status);
        if(subError != null) {
            this.message = subError.getMessage();
            this.subErrors.add(subError);
        }
    }

    /**
     * Добавить внутреннюю ошибку.
     * @param subError - ошибка
     * @return цепь вызова
     */
    public ApiError add(ApiSubError subError){
        subErrors.add(subError);
        return this;
    }
}
