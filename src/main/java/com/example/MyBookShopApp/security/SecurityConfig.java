package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.security.jwt.JWTRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BookstoreUserDetailsService bookstoreUserDetailsService;
    private final BookstoreOAuth2UserService bookstoreOAuth2UserService;
    private final JWTRequestFilter filter;
    private final BookstoreLogoutHandler logoutHandler;

    @Autowired
    public SecurityConfig(BookstoreUserDetailsService bookstoreUserDetailsService, BookstoreOAuth2UserService bookstoreOAuth2UserService, JWTRequestFilter filter, BookstoreLogoutHandler logoutHandler) {
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
        this.bookstoreOAuth2UserService = bookstoreOAuth2UserService;
        this.filter = filter;
        this.logoutHandler = logoutHandler;
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(bookstoreUserDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/my", "/profile").authenticated()//.hasRole("USER")
                .antMatchers("/**").permitAll()
                .and().formLogin()
                .loginPage("/signin").failureUrl("/signin")
                .and().logout().logoutUrl("/logout").addLogoutHandler(logoutHandler).logoutSuccessUrl("/signin").deleteCookies("token")
                .and().oauth2Login().loginPage("/signin").userInfoEndpoint().userService(bookstoreOAuth2UserService)
                .and().successHandler((request, response, authentication) -> {
                    BookstoreOAuth2User oidcUser = (BookstoreOAuth2User) authentication.getPrincipal();
                    bookstoreUserDetailsService.processOAuthPostLogin(oidcUser);
                    response.sendRedirect("/my");
                });

        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
