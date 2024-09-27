package com.example.google_oauth_2.auth.dto;

public record GoogleResourceDto(
        String id,
        String email,
        String picture,
        String nickname
) {}