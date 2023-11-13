package com.luminicel.mailing.domain.model;

public interface Email {
    String getFrom();

    String getRecipient();

    String getSubject();

    String getBody();
}
