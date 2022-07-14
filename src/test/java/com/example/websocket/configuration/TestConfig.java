package com.example.websocket.configuration;

import com.example.websocket.SimpleWebsocketServiceApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SimpleWebsocketServiceApplication.class)
public class TestConfig {


}
