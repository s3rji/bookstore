package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.security.RegistrationForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.MyBookShopApp.UserTestData.getNewRegistrationForm;
import static com.example.MyBookShopApp.UserTestData.getTestUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class AuthUserControllerTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthUserControllerTests(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void userRegistrationTest() throws Exception {
        RegistrationForm newForm = getNewRegistrationForm();
        mockMvc.perform(post("/reg")
                        .contentType("application/x-www-form-urlencoded")
                        .param("name", newForm.getName())
                        .param("phone", newForm.getPhone())
                        .param("email", newForm.getEmail())
                        .param("pass", newForm.getPass()))
                .andDo(print())
                .andExpect(flash().attributeExists("regOk"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/signin"));
    }

    @Test
    void userRegistrationFailTest() throws Exception {
        mockMvc.perform(post("/reg")
                        .contentType("application/x-www-form-urlencoded")
                        .param("name", getTestUser().getName())
                        .param("phone", getTestUser().getPhone())
                        .param("email", getTestUser().getEmail())
                        .param("pass", getTestUser().getPassword()))
                .andDo(print())
                .andExpect(flash().attributeExists("errorInfo"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/signup"));
    }

    @Test
    void correctLoginTest() throws Exception {
        mockMvc.perform(formLogin("/signin")
                        .user(getTestUser().getName())
                        .password(getTestUser().getPassword()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void failLoginTest() throws Exception {
        mockMvc.perform(formLogin("/signin")
                        .user("UserNotExist@mail.ru")
                        .password("password"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/signin"));
    }

    @Test
    void logoutTest() throws Exception {
        mockMvc.perform(logout())
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/signin"));
    }
}