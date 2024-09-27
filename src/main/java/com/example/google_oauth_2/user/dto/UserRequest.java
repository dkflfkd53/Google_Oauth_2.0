package com.example.google_oauth_2.user.dto;

public record UserRequest(
        String email,
        String password,
        String picture,
        String nickname
) {}