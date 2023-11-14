package com.luminicel.tenant.tenant.domain.service;

import com.luminicel.mailing.domain.model.Email;
import com.luminicel.mailing.domain.service.EmailSender;
import com.luminicel.tenant.tenant.application.TenantRepository;
import com.luminicel.tenant.tenant.domain.model.Status;
import com.luminicel.tenant.tenant.domain.model.Tenant;
import com.luminicel.tenant.tenant.domain.model.TenantForm;
import com.luminicel.tenant.tenantConfig.TenantConfigService;
import com.luminicel.tenant.user.User;
import com.luminicel.tenant.user.UserRepository;
import com.luminicel.tenant.util.TokenGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

@Component
public class CreateTenant {
    private static final ZoneId zone = ZoneId.of("America/Mexico_City");
    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final TenantConfigService configService;

    private final PasswordEncoder passwordEncoder;

    private final EmailSender emailSender;
    private Email email;
    private Tenant tenant;
    private User user;

    private CreateTenant(final TenantRepository repository, UserRepository userRepository, TenantConfigService configService, final PasswordEncoder passwordEncoder, final EmailSender emailSender) {
        this.tenantRepository = repository;
        this.userRepository = userRepository;
        this.configService = configService;
        this.emailSender = emailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public String create(TenantForm form) {
        checkIfEmailIsRepeated(form.email());
        createUserAndTenant(form);
        setEmailInformation();
        setDefaultConfig();
        // TODO SendEmail
//        tryToSendEmail();
        return getSuccessMessage();
    }

    private void setDefaultConfig() {
        configService.createDefaultConfigForTenant(tenant);
    }

    private void createUserAndTenant(TenantForm form){
        final String encryptedPassword = passwordEncoder.encode(form.password());
        user = User.builder()
                .firstname(form.firstname())
                .lastname(form.lastname())
                .email(form.email())
                .password(encryptedPassword)
                .isActive(true)
                .build();
        userRepository.save(user);

        tenant = Tenant.builder()
                .domain(form.domain())
                .status(Status.PROSPECTIVE)
                .user_id(user.getId())
                .build();
        tenantRepository.save(tenant);
    }

    private void checkIfEmailIsRepeated(String email) {
        final boolean exists = userRepository.existsByEmail(email);
        if (exists) {
            throw new IllegalArgumentException(getEmailRepeatedWarning(email));
        }
    }

    private String getEmailRepeatedWarning(String email) {
        return String.format("Tenant with email %s is already registered", email);
    }


    private void setEmailInformation() {
        final String token = getToken(user.getEmail());
        email = VerifyEmailGenerator.create(token, user.getEmail(), user.getFirstname(), user.getPassword()).generate();
    }

    private String getToken(final String email) {
        final ZonedDateTime expiration = getExpiration();
        final Map<String, Object> claims = Map.of(
                "email", email
        );
        final TokenGenerator tokenGenerator = TokenGenerator.builder()
                .subject("verification")
                .claims(claims)
                .expiration(expiration)
                .build();
        return tokenGenerator.generateSignedToken();
    }

    private ZonedDateTime getExpiration() {
        return ZonedDateTime.now(zone)
                .plusMinutes(15);
    }

    private void tryToSendEmail() {
        final boolean success = emailSender.sendEmail(email);
        if (!success) {
            throw new IllegalArgumentException(getEmailSenderWarning());
        }
    }

    private String getEmailSenderWarning() {
        return String.format("An error occured while sending email to %s", user.getEmail());
    }

    private String getSuccessMessage() {
        return String.format("Tenant %s successfully inserted", user.getFirstname());
    }
}

