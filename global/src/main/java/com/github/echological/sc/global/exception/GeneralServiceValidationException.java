package com.github.echological.sc.global.exception;

import lombok.Getter;

import java.util.List;

public class GeneralServiceValidationException extends RuntimeException {

    private static final long serialVersionUID = -4229568314395734158L;

    @Getter
    private final Class<?> serviceClass;
    @Getter
    private final List<String> error;

    public GeneralServiceValidationException(
            String message,
            Throwable cause,
            Class<?> serviceClass,
            List<String> error) {
        super(message, cause);
        this.serviceClass = serviceClass;
        this.error = error;
    }
}
