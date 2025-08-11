package com.avrist.sc.global.global.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

public class BusinessServiceValidationException extends RuntimeException {

    private static final long serialVersionUID = -4229568314395734158L;

    @Getter
    private final String responseCode;
    @Getter
    private final String responseMessage;
    @Getter
    private final HttpStatus httpStatus;
    @Getter
    private final List<String> error;


    public BusinessServiceValidationException(
            String responseCode,
            String responseMessage,
            HttpStatus httpStatus,
            List<String> error) {
        super(responseMessage);
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.httpStatus = httpStatus;
        this.error = error;
    }

    public BusinessServiceValidationException(
            String responseCode,
            String responseMessage,
            HttpStatus httpStatus,
            List<String> error,
            String exceptionMsg,
            Throwable cause) {
        super(exceptionMsg, cause);
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.httpStatus = httpStatus;
        this.error = error;
    }
}
