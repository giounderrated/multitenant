package com.luminicel.mailing.domain.service;

import com.luminicel.mailing.domain.model.Email;

public interface EmailSender {
    boolean sendEmail(Email email);
}
