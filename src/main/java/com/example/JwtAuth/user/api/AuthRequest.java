package com.example.JwtAuth.user.api;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class AuthRequest {
    @NotNull
    @Email
    @Length(min = 5, max = 50)
    private String email;
    @NotNull
    @Length(min = 8, max = 16)
    private String password;
    @NotNull
    private boolean locked;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
