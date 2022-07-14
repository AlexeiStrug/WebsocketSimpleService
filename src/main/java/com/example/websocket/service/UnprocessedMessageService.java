package com.example.websocket.service;


import com.example.websocket.domain.Message;

public interface UnprocessedMessageService {

    void saveUnprocessedMessage(Message message);
}
