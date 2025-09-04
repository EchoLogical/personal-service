package com.github.echological.sc.global.util;

import com.github.echological.sc.global.constant.ServiceStatus;
import com.github.echological.sc.global.exception.BusinessServiceValidationException;
import com.github.echological.sc.global.lang.MessageEn;
import com.github.echological.sc.global.lang.MessageIn;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.util.List;

@UtilityClass
public class SysMessageUtil {

    public static String getMessage(String lang, String field) {
        String code = (lang == null) ? "en" : lang.toLowerCase();
        String key = (field == null) ? "" : field.toUpperCase();

        try {
            return switch (code) {
                case "en" -> (String) MessageEn.class.getField(key).get(null);
                case "in", "id" -> (String) MessageIn.class.getField(key).get(null);
                default -> (String) MessageEn.class.getField(key).get(null);
            };
        }catch (Exception e){
            throw new BusinessServiceValidationException(
                    ServiceStatus.MESSAGE_NOT_READABLE.getCode(),
                    ServiceStatus.MESSAGE_NOT_READABLE.getStatus(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    List.of("Invalid language or message key")
            );
        }

    }


}
