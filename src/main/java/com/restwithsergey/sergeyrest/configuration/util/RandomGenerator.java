package com.restwithsergey.sergeyrest.configuration.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class RandomGenerator {
    public final Random RANDOM= new SecureRandom();
    public final String CHARACTER_STRING = "0123456789@#$%*ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    public String generateUserId(int length){
        return generateRandomString(length);
    }

    private String generateRandomString(int length){
        StringBuilder stringBuilder = new StringBuilder(length);

        for(int i=0; i<length; i++){
            stringBuilder.append(CHARACTER_STRING.charAt(RANDOM.nextInt(CHARACTER_STRING.length())));
        }

        return new String(stringBuilder);
    }
}
