package com.luminicel.mailing.domain.model;

import lombok.Builder;

@Builder
public class EmailImpl implements Email{
    private final String from;
    private final String recipient;
    private final String subject;
    private final String body;

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public String getRecipient() {
        return recipient;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public String getBody() {
        return body;
    }
}
