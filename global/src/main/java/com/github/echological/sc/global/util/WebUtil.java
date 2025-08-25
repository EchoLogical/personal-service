package com.github.echological.sc.global.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WebUtil {

    public static boolean isBrowser(String userAgent) {
        return userAgent.contains("Mozilla") || userAgent.contains("Chrome") || userAgent.contains("Safari") || userAgent.contains("Edge");
    }

}
