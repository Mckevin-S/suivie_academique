package com.suivie_academique.suivie_academique.dto;



public class LoginResponse {
    private String token;

    public LoginResponse(String token) { this.token = token; }

    public String getToken() { return token; }
}
