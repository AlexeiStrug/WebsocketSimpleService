package com.example.websocket.service.kafka;


import com.example.websocket.configuration.builders.MessageModelBuilder;
import com.example.websocket.domain.Message;
import com.example.websocket.service.UnprocessedMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.concurrent.SettableListenableFuture;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProducerWithFallbackTest {

    @Mock
    KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    UnprocessedMessageService unprocessedEventService;

    @InjectMocks
    ProducerWithFallback testedObj;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(testedObj, "topicName", "test-topic");
    }

    @Test
    @SneakyThrows
    void write_shouldSendMessage() {
        //given
        final var input = new MessageModelBuilder().build();
        SettableListenableFuture<SendResult<String, String>> future = new SettableListenableFuture<>();

        //when
        when(objectMapper.writeValueAsString(isA(Message.class))).thenReturn(input.toString());
        when(kafkaTemplate.send(isA(String.class), isA(String.class))).thenReturn(future);

        //then
        testedObj.write(input);

        verify(kafkaTemplate, times(1)).send(isA(String.class), isA(String.class));
        verify(unprocessedEventService, never()).saveUnprocessedMessage(isA(Message.class));
    }

    @Test
    @SneakyThrows
    void write_shouldNotSendMessage_throwException_AndWriteToDb() {
        //given
        final var input = new MessageModelBuilder().build();
        SettableListenableFuture<SendResult<String, String>> future = new SettableListenableFuture<>();
        future.setException(new RuntimeException());

        //when
        when(objectMapper.writeValueAsString(isA(Message.class))).thenReturn(input.toString());
        when(kafkaTemplate.send(isA(String.class), isA(String.class))).thenReturn(future);

        //then
        testedObj.write(input);

        verify(kafkaTemplate, times(1)).send(isA(String.class), isA(String.class));
        verify(unprocessedEventService, times(1)).saveUnprocessedMessage(isA(Message.class));
    }
}
