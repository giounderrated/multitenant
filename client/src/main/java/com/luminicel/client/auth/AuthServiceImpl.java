package com.luminicel.client.auth;

import com.luminicel.client.users.User;
import com.luminicel.client.users.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LoginCredentials getLoginCredentials(LoginForm form) {
        final User user = userRepository.findByEmail(form.email()).orElseThrow(
                ()-> new IllegalArgumentException(String.format("No such user with email",form.email()))
        );
        return new LoginCredentials(
                user.getId(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getRole().name()
        );
    }
}
