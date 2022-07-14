package com.example.websocket.service.impl;

import com.example.websocket.domain.Message;
import com.example.websocket.domain.UnprocessedMessage;
import com.example.websocket.repository.UnprocessedMessageRepository;
import com.example.websocket.service.UnprocessedMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UnprocessedMessageServiceImpl implements UnprocessedMessageService {

    private final UnprocessedMessageRepository unprocessedMessageRepository;

    @Override
    public void saveUnprocessedMessage(Message message) {
        final var unprocessedMessage = buildUnprocessedEvent(message);
        saveToDb(unprocessedMessage);
    }

    @Transactional
    private void saveToDb(UnprocessedMessage unprocessedMessage) {
        unprocessedMessageRepository.save(unprocessedMessage);
    }

    private UnprocessedMessage buildUnprocessedEvent(Message message) {
        return UnprocessedMessage.builder()
                .message(message)
                .build();
    }
}
