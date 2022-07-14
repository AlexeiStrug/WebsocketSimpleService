package com.example.websocket.configuration.builders;

import com.example.websocket.domain.Message;

import java.sql.Timestamp;

public class MessageModelBuilder {

    private static final Timestamp COMMON_DATE = new Timestamp(System.currentTimeMillis());

    private Long id = 1L;
    private String body = "Sample message from user";
    private String userId = "Sample user id";
    private Timestamp createdAt = COMMON_DATE;
    private Timestamp updatedAt = COMMON_DATE;


    public MessageModelBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public MessageModelBuilder withBody(String body) {
        this.body = body;
        return this;
    }

    public MessageModelBuilder withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public MessageModelBuilder withCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public MessageModelBuilder withUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Message build() {
        return Message.builder()
                .id(this.id)
                .body(this.body)
                .userId(this.userId)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

}
