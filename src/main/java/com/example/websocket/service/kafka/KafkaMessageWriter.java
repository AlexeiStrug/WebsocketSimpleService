package com.example.websocket.service.kafka;

import com.example.websocket.domain.Message;

public interface KafkaMessageWriter {

    void write(Message message);
}
