package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Service
public class BookstoreLogoutHandler implements LogoutHandler {

    private final JwtBlacklistRepository jwtBlacklistRepository;
    private final JWTUtil jwtUtil;

    @Autowired
    public BookstoreLogoutHandler(JwtBlacklistRepository repository, JWTUtil jwtUtil) {
        this.jwtBlacklistRepository = repository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Cookie cookieToken = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("token"))
                    .findFirst()
                    .orElse(null);

            if (cookieToken != null) {
                JwtBlacklist jwtBlacklist = new JwtBlacklist();
                jwtBlacklist.setToken(cookieToken.getValue());
                jwtBlacklist.setExpiringDate(jwtUtil.extractExpiration(cookieToken.getValue()));
                jwtBlacklistRepository.save(jwtBlacklist);
            }
        }
    }
}
