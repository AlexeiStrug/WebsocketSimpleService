package com.example.websocket.service;

import com.example.websocket.controller.dto.MessageDto;

public interface MessageService {

    void saveMessage(MessageDto dto);
}
