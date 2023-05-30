package com.example.JwtAuth.user.api;

import java.util.Date;

public class AuthResponse {
    private String accessToken;
    private Date accessTokenExpirationDate;

    public AuthResponse() {
    }

    public AuthResponse(String accessToken, Date accessTokenExpirationDate) {
        this.accessToken = accessToken;
        this.accessTokenExpirationDate = accessTokenExpirationDate;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getAccessTokenExpirationDate() {
        return accessTokenExpirationDate;
    }

    public void setAccessTokenExpirationDate(Date accessTokenExpirationDate) {
        this.accessTokenExpirationDate = accessTokenExpirationDate;
    }

}

