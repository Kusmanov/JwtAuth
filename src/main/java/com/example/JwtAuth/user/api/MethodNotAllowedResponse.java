package com.example.JwtAuth.user.api;

public class MethodNotAllowedResponse {
    private String response;

    public MethodNotAllowedResponse() {
    }

    public MethodNotAllowedResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
