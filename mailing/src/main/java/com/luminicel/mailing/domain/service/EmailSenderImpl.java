package com.luminicel.mailing.domain.service;

import com.luminicel.mailing.domain.model.Email;
import com.sun.mail.smtp.SMTPTransport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Objects;
import java.util.Properties;

@Slf4j
@Service
public class EmailSenderImpl implements EmailSender {
    private static final String MODULE = "EMAIL";


    private static final String PORT = "2525";
    private static final String PROTOCOL = "smtp";
    private static final String SMTP_SERVER = "sandbox.smtp.mailtrap.io";
    private final String USERNAME = "4ce336d720d1c3";
    private final String PASSWORD = "e2996ff9b7f73f";
    private Email email;
    @Override
    public boolean sendEmail(Email email) {
        setEmail(email);
        return tryToSendEmail();
    }

    private void setEmail(Email email) {
        this.email = Objects.requireNonNull(email);
    }

    private boolean tryToSendEmail() {
        final Properties properties = getProperties();
        final Session session = getSession(properties);

        final Message message = getMessage(session);

        try {
            final SMTPTransport transport = (SMTPTransport) session.getTransport(PROTOCOL);
            transport.connect(SMTP_SERVER, USERNAME, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            logInfo("SMTP Response: " + transport.getLastServerResponse());
            return true;
        } catch (final MessagingException e) {
            logError(e);
            return false;
        }
    }

    private Message getMessage(Session session) {
        try {
            final Message message = new MimeMessage(session);
            final Message.RecipientType to = Message.RecipientType.TO;
            final InternetAddress[] recipients = InternetAddress.parse(email.getRecipient(), false);
            message.setRecipients(to, recipients);
            message.setFrom(new InternetAddress(email.getFrom()));
            message.setSubject(email.getSubject());

            // * BODY
            final BodyPart body = new MimeBodyPart();
            body.setContent(email.getBody(), "text/html; charset=UTF-8");
            final Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(body);

            message.setContent(multipart);
            return message;
        } catch (final Exception e) {
            logError(e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Session getSession(Properties properties) {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
    }

    private Properties getProperties() {
        final Properties properties = System.getProperties();
        properties.setProperty("mail.smtps.host", SMTP_SERVER);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", PORT);
        properties.setProperty("mail.smtp.starttls.enable", "true");
        return properties;
    }

    private static void logInfo(final String message) {
        final String string = getFormattedMessage(message);
        log.info(string);
    }

    private static void logError(final Exception e) {
        final String message = getFormattedMessage(e.getMessage());
        log.error(message, e);
    }

    private static String getFormattedMessage(final String message) {
        return String.format("%s#~#%s", message, MODULE);
    }

}