package com.luminicel.client.auth;

public interface AuthService {
    LoginCredentials authenticate(LoginForm form);
}
