package com.example.MyBookShopApp;

import com.example.MyBookShopApp.model.user.User;
import com.example.MyBookShopApp.security.RegistrationForm;

public class UserTestData {
    public static final int USER_ID = 1;

    public static RegistrationForm getNewRegistrationForm() {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setName("TestUser");
        registrationForm.setEmail("TestUser@mail.ru");
        registrationForm.setPhone("89259950011");
        registrationForm.setPass("password");
        return registrationForm;
    }

    public static User getTestUser() {
        User user = new User("TestUser");
        user.setId(USER_ID);
        user.setEmail("TestUser@mail.ru");
        user.setPhone("89259950011");
        user.setPassword("password");
        user.setBalance(0);
        return user;
    }
}
