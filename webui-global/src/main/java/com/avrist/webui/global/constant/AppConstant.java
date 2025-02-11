package com.avrist.webui.global.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppConstant {
    public static final String RANDOM_CHARACTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()";

    public static final String TOKEN_SECRET_KEY = "TOKEN_SECRET_KEY";
    public static final String TOKEN_SECRET_KEY_DESC = "Token secret key.";

    public static final String REFRESH_TOKEN_SECRET_KEY = "REFRESH_TOKEN_SECRET_KEY";
    public static final String REFRESH_TOKEN_SECRET_KEY_DESC = "Refresh token secret key.";

    public static final String TOKEN_EXPIRY = "TOKEN_EXPIRY";
    public static final String TOKEN_EXPIRY_DESC = "Token expiry in hour.";

    public static final String REFRESH_TOKEN_EXPIRY = "REFRESH_TOKEN_EXPIRY";
    public static final String REFRESH_TOKEN_EXPIRY_DESC = "Refresh token expiry in day.";
}
