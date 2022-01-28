package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.model.user.User;
import com.example.MyBookShopApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class BookstoreUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public BookstoreUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return new BookstoreUserDetails(user);
        } else {
            throw new UsernameNotFoundException("User not found!");
        }
    }

    public void processOAuthPostLogin(BookstoreOAuth2User oAuth2User) {
        if (userRepository.findByEmail(oAuth2User.getEmail()) == null) {
            User user = new User();
            user.setName(oAuth2User.getName());
            user.setEmail(oAuth2User.getEmail());
            user.setHash(user.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss")));
            userRepository.save(user);
        }
    }
}
