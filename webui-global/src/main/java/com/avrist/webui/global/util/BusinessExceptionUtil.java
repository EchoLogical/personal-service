package com.avrist.webui.global.util;

import com.avrist.core.constant.AVRStatus;
import com.avrist.webui.global.exception.BusinessServiceValidationException;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.util.Collections;

@UtilityClass
public class BusinessExceptionUtil {
    public static BusinessServiceValidationException notFoundException(String message) {
        return new BusinessServiceValidationException(
                AVRStatus.DATA_NOT_FOUND.getCode(),
                AVRStatus.DATA_NOT_FOUND.getStatus(),
                HttpStatus.NOT_FOUND,
                Collections.singletonList(message));
    }

    public static BusinessServiceValidationException badRequestException(String message) {
        return new BusinessServiceValidationException(
                AVRStatus.FAILED.getCode(),
                AVRStatus.FAILED.getStatus(),
                HttpStatus.BAD_REQUEST,
                Collections.singletonList(message));
    }

    public static BusinessServiceValidationException serverException(String message) {
        return new BusinessServiceValidationException(
                AVRStatus.ERROR.getCode(),
                AVRStatus.ERROR.getStatus(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                Collections.singletonList(message));
    }
}
