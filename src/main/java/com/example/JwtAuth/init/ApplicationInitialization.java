package com.example.JwtAuth.init;

import com.example.JwtAuth.user.User;
import com.example.JwtAuth.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class ApplicationInitialization {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void createUser() {
        if (userRepository.findByEmail("super@admin.net").isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = passwordEncoder.encode("password");
            String scope = "super";
            User newUser = new User("super@admin.net", password, scope);
            User savedUser = userRepository.save(newUser);
            assertThat(savedUser).isNotNull();
            assertThat(savedUser.getId()).isGreaterThan(0);
        }
    }

}
