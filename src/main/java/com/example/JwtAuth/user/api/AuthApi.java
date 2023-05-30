package com.example.JwtAuth.user.api;

import com.example.JwtAuth.jwt.JwtTokenUtil;
import com.example.JwtAuth.user.User;
import com.example.JwtAuth.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.assertj.core.api.Assertions.assertThat;

@RestController
@RequestMapping("/auth")
public class AuthApi {
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtTokenUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(accessToken, jwtUtil.getExpirationDate());
            return ResponseEntity.ok().body(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/change/password")
    public ResponseEntity<?> changePassword(@RequestBody AuthRequest request) {
        User existingUser = userRepository.findByEmail(request.getEmail()).orElse(null);
        assertThat(existingUser).isNotNull();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(request.getPassword());

        existingUser.setPassword(password);

        User updatedUser = userRepository.save(existingUser);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getEmail()).isEqualTo(existingUser.getEmail());

        return ResponseEntity.ok().body(updatedUser);
    }

    @PostMapping("/change/locked")
    public ResponseEntity<?> changeLocked(@RequestBody AuthRequest request) {
        User existingUser = userRepository.findByEmail(request.getEmail()).orElse(null);
        assertThat(existingUser).isNotNull();

        if (!existingUser.getScope().equals("super")) {
            existingUser.setLocked(!existingUser.isLocked());

            User updatedUser = userRepository.save(existingUser);
            assertThat(updatedUser).isNotNull();
            assertThat(updatedUser.getEmail()).isEqualTo(existingUser.getEmail());

            return ResponseEntity.ok().body(updatedUser);
        }
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new MethodNotAllowedResponse("impossible for super"));
    }

    @GetMapping("/info")
    public ResponseEntity<?> info(@RequestBody AuthRequest request) {
        User existingUser = userRepository.findByEmail(request.getEmail()).orElse(null);
        assertThat(existingUser).isNotNull();

        return ResponseEntity.ok().body(existingUser);
    }
}