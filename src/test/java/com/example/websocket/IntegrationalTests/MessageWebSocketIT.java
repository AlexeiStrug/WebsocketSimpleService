package com.example.websocket.IntegrationalTests;

import com.example.websocket.configuration.IT;
import com.example.websocket.configuration.builders.MessageDtoBuilder;
import com.example.websocket.repository.MessageRepository;
import com.example.websocket.repository.UnprocessedMessageRepository;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Type;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.codehaus.groovy.runtime.InvokerHelper.asList;

@IT
public class MessageWebSocketIT {

    private static final String WEBSOCKET_URI = "ws://localhost:8080/stomp-endpoint";
    private static final String WEBSOCKET_TOPIC = "/app/chat";

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UnprocessedMessageRepository unprocessedMessageRepository;

    ArrayBlockingQueue<String> blockingQueue;
    WebSocketStompClient stompClient;

    ObjectMapper objectMapper;


    @BeforeEach
    void cleanUpDB() {
        this.messageRepository.deleteAll();
        this.unprocessedMessageRepository.deleteAll();
        blockingQueue = new ArrayBlockingQueue<>(1);
        objectMapper = new ObjectMapper();
        stompClient = new WebSocketStompClient(new SockJsClient(asList(new WebSocketTransport(new StandardWebSocketClient()))));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    @Test
    @SneakyThrows
    void send_shouldGetMessage_AndSendToKafka() {
        //given
        final var dto = new MessageDtoBuilder().build();

        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {
                })
                .get(1, SECONDS);
        subscribe("/topic/message-response", blockingQueue, session);

        //when
        session.send(WEBSOCKET_TOPIC, dto);

        //then
        final var result = blockingQueue.poll(1, SECONDS);

        TimeUnit.SECONDS.sleep(100);
        final var messages = messageRepository.findAll();
        final var unprocessedMessages = unprocessedMessageRepository.findAll();
        assertThat(messages.spliterator().getExactSizeIfKnown()).isEqualTo(1);
        assertThat(unprocessedMessages.spliterator().getExactSizeIfKnown()).isEqualTo(1);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("Message Successfully proceed.");
    }

    private void subscribe(String topic, ArrayBlockingQueue<String> blockingQueue, StompSession session) {
        session.subscribe(topic, new StompFrameHandler() {
            @Override
            @NonNull
            public Type getPayloadType(@NonNull StompHeaders headers) {
                return Object.class;
            }

            @Override
            @SneakyThrows
            public void handleFrame(@NonNull StompHeaders headers, Object payload) {
                blockingQueue.add(objectMapper.readValue((byte[]) payload, String.class));
            }
        });
    }

}
