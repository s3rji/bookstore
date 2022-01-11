package com.example.MyBookShopApp.util;

public class SecurityUtil {
    private final static Integer userId = 1000;

    private SecurityUtil() {
    }

    //mock auth user
    public static Integer authUserId() {
        return userId;
    }
}
