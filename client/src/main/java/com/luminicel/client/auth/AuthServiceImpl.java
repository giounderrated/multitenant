package com.luminicel.client.auth;

import com.luminicel.client.config.security.JwtService;
import com.luminicel.client.multitenancy.TenantContext;
import com.luminicel.client.users.User;
import com.luminicel.client.users.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public LoginCredentials authenticate(LoginForm form) {
        final User user = userRepository.findByEmail(form.email()).orElseThrow(
                () -> new IllegalArgumentException(String.format("No such user with email %s", form.email()))
        );

        if (!passwordEncoder.matches(form.password(), user.getPassword())) {
            throw new IllegalArgumentException("Email or password incorrect");
        }

        Map<String,Object> customClaims = new HashMap<>();
        customClaims.put("firstname",user.getFirstname());
        customClaims.put("lastname",user.getLastname());
        customClaims.put("email",user.getEmail());
        customClaims.put("role",user.getRole().name());
        customClaims.put("profilePicture", user.getProfilePicture());
        customClaims.put("tenantId", TenantContext.getTenantId());

        final String token = jwtService.generateToken(customClaims,user);

        final LoginCredentials credentials = new LoginCredentials(
                user.getEmail(),
                token
        );

        return credentials;
    }
}
