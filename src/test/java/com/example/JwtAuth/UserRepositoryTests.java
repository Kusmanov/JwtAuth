package com.example.JwtAuth;

import com.example.JwtAuth.user.User;
import com.example.JwtAuth.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

    @Test
    public void testCreateUser() {
        Random random = new Random();
        String randomWord = "";
        for (int i = 0; i < 6; i++) {
            int asciiCode = random.nextInt(26) + 97; // Генерируем случайное число от 97 до 122, соответствующее буквам английского алфавита в нижнем регистре
            char randomChar = (char) asciiCode;
            randomWord += randomChar; // Добавляем символ к строке
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("nam2020");
        User newUser = new User(randomWord + "@codejava.net", password);
        User savedUser = repo.save(newUser);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }
}
