package com.example.websocket.configuration.builders;

import com.example.websocket.domain.Message;
import com.example.websocket.domain.UnprocessedMessage;

import java.sql.Timestamp;

public class UnprocessedMessageModelBuilder {

    private static final Timestamp COMMON_DATE = new Timestamp(System.currentTimeMillis());

    private Long id = 1L;
    private Message message = new MessageModelBuilder().build();
    private Timestamp createdAt = COMMON_DATE;
    private Timestamp updatedAt = COMMON_DATE;

    public UnprocessedMessageModelBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UnprocessedMessageModelBuilder withMessage(Message message) {
        this.message = message;
        return this;
    }

    public UnprocessedMessageModelBuilder withCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UnprocessedMessageModelBuilder withUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public UnprocessedMessage build() {
        return UnprocessedMessage.builder()
                .id(this.id)
                .message(this.message)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

}
