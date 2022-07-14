package com.example.websocket.service.impl;

import com.example.websocket.controller.dto.MessageDto;
import com.example.websocket.domain.Message;
import com.example.websocket.mapper.MessageMapper;
import com.example.websocket.repository.MessageRepository;
import com.example.websocket.service.MessageService;
import com.example.websocket.service.kafka.KafkaMessageWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final KafkaMessageWriter kafkaEventWriter;
    private final MessageMapper messageMapper;

    @Override
    public void saveMessage(MessageDto dto) {
        final var message = messageMapper.dtoToModel(dto);
        saveToDatabaseAndSendToKafka(message);
    }

    public void saveToDatabaseAndSendToKafka(Message message) {
        Function<Message, Message> save = (value) -> saveToDb(message);
        Consumer<Message> send = (value) -> writeMessage(message);

        send.accept(save.apply(message));
    }

    @Transactional
    private Message saveToDb(Message message) {
        return messageRepository.save(message);
    }

    private void writeMessage(Message message) {
        kafkaEventWriter.write(message);
    }
}
