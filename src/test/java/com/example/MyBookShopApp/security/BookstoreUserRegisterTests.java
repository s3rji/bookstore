package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.dto.ApiResult;
import com.example.MyBookShopApp.model.user.User;
import com.example.MyBookShopApp.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import static com.example.MyBookShopApp.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BookstoreUserRegisterTests {

    private final BookstoreUserRegister userRegister;
    private final PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepositoryMock;

    @MockBean
    private AuthenticationManager authenticationManagerMock;

    @MockBean
    BookstoreUserDetailsService userDetailsServiceMock;

    @Autowired
    public BookstoreUserRegisterTests(BookstoreUserRegister userRegister, PasswordEncoder passwordEncoder) {
        this.userRegister = userRegister;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void registerNewUser() {
        User user = userRegister.registerNewUser(getNewRegistrationForm());
        user.setId(USER_ID);
        assertNotNull(user);
        assertTrue(passwordEncoder.matches(getNewRegistrationForm().getPass(), user.getPassword()));
        assertThat(user).usingRecursiveComparison().ignoringFields("hash", "regTime", "password").isEqualTo(getTestUser());

        Mockito.verify(userRepositoryMock, Mockito.times(1))
                .save(Mockito.any(User.class));
    }

    @Test
    void login() {
        ContactConfirmationPayload payload = new ContactConfirmationPayload();
        payload.setContact("TestUser@mail.ru");
        payload.setCode("password");
        ApiResult response = userRegister.login(payload);
        assertNotNull(response);
        assertTrue(response.getResult());
    }

    @Test
    void jwtLogin() {
        Mockito.doReturn(new BookstoreUserDetails(getTestUser()))
                .when(userDetailsServiceMock)
                .loadUserByUsername(getTestUser().getEmail());

        ContactConfirmationPayload payload = new ContactConfirmationPayload();
        payload.setContact("TestUser@mail.ru");
        payload.setCode("password");
        ContactConfirmationResponse response = userRegister.jwtLogin(payload);
        assertNotNull(response);
        assertTrue(StringUtils.hasLength(response.getResult()));
    }
}