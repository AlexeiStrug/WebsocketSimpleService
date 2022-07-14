package com.example.websocket.controller;

import com.example.websocket.controller.dto.MessageDto;
import com.example.websocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageWebSocketController {

    private final MessageService messageService;

    @MessageMapping("/chat")
    @SendToUser("/topic/message-response")
    public String send(@Payload MessageDto dto) {
        messageService.saveMessage(dto);
        return "Message Successfully proceed.";
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
}
