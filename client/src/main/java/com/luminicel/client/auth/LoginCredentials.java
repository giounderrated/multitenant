package com.luminicel.client.auth;

public record LoginCredentials(
        Long id,
        String email,
        String firstname,
        String lastname,
        String role
) {
}
