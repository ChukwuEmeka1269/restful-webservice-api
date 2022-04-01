package com.restwithsergey.sergeyrest.configuration.security;

import com.restwithsergey.sergeyrest.AppApplicationContext;
import com.restwithsergey.sergeyrest.configuration.util.AppProperties;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/create";

    public static String getTokenSecret(){
        AppProperties appProperties = (AppProperties) AppApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }

}
