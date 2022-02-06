package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.dto.ApiResult;
import com.example.MyBookShopApp.ex.IllegalRequestDataException;
import com.example.MyBookShopApp.model.user.User;
import com.example.MyBookShopApp.repository.UserRepository;
import com.example.MyBookShopApp.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class BookstoreUserRegister {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BookstoreUserDetailsService bookstoreUserDetailsService;
    private final JWTUtil jwtUtil;

    @Autowired
    public BookstoreUserRegister(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, BookstoreUserDetailsService bookstoreUserDetailsService, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public User registerNewUser(RegistrationForm registrationForm) {
        if (userRepository.findByEmail(registrationForm.getEmail()) == null) {
            User user = new User();
            user.setName(registrationForm.getName());
            user.setEmail(registrationForm.getEmail());
            user.setPhone(registrationForm.getPhone());
            user.setPassword(passwordEncoder.encode(registrationForm.getPass()));
            user.setHash(user.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss")));
            userRepository.save(user);
            return user;
        } else {
            throw new IllegalRequestDataException("User already exist");
        }
    }

    public ApiResult login(ContactConfirmationPayload payload) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                        payload.getCode()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ApiResult(true);
    }

    public ContactConfirmationResponse jwtLogin(ContactConfirmationPayload payload) {
        BookstoreUserDetails userDetails =
                (BookstoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(payload.getContact());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                payload.getCode()));

        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            BookstoreUserDetails userDetails = (BookstoreUserDetails) principal;
            return userDetails.getBookstoreUser();
        } else if (principal instanceof OAuth2User) {
            BookstoreOAuth2User oAuth2User = (BookstoreOAuth2User) principal;
            return oAuth2User.getBookstoreUser();
        }
        return new User("anonymousUser");
    }
}
