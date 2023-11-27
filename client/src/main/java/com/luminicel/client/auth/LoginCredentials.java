package com.luminicel.client.auth;

public record LoginCredentials(
        String email,
        String token
) {
}
