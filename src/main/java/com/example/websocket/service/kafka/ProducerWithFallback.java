package com.example.websocket.service.kafka;

import com.example.websocket.domain.Message;
import com.example.websocket.service.UnprocessedMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProducerWithFallback implements KafkaMessageWriter {

    @Value("${producer.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final UnprocessedMessageService unprocessedEventService;

    @Override
    public void write(Message message) {
        try {
            kafkaTemplate
                    .send(topicName, convertToString(message))
                    .addCallback(getListenableFutureCallback(message));
        } catch (Exception ex) {
            log.error("Failure Connect to Kafka: Unable to deliver message [{}]. {}", message, ex.getMessage());
            unprocessedEventService.saveUnprocessedMessage(message);
        }
    }

    private String convertToString(Message message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private ListenableFutureCallback<SendResult<String, String>> getListenableFutureCallback(Message message) {
        return new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Success Callback: [{}] delivered with offset -{}", message, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Failure Callback: Unable to deliver message [{}]. {}", message, ex.getMessage());
                unprocessedEventService.saveUnprocessedMessage(message);
            }
        };
    }
}
