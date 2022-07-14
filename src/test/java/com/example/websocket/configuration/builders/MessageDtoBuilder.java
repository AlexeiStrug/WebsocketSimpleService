package com.example.websocket.configuration.builders;


import com.example.websocket.controller.dto.MessageDto;
import com.fasterxml.jackson.core.JsonParser;

public class MessageDtoBuilder {

    private String body = "Sample message from user";
    private String userId = "Sample user id";

    public MessageDtoBuilder withBody(String body) {
        this.body = body;
        return this;
    }

    public MessageDtoBuilder withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public MessageDto build() {
        return MessageDto.builder()
                .body(this.body)
                .userId(this.userId)
                .build();
    }
}
