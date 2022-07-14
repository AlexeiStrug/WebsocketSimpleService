package com.example.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableKafka
@SpringBootApplication
@EnableTransactionManagement
public class SimpleWebsocketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleWebsocketServiceApplication.class, args);
    }

}
