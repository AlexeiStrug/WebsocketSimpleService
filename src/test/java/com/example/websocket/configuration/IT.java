package com.example.websocket.configuration;

import com.example.websocket.configuration.common.kafka.KafkaExtension;
import com.example.websocket.configuration.common.postgres.PostgresDbExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(TYPE)
@ExtendWith({PostgresDbExtension.class, KafkaExtension.class})
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Inherited
public @interface IT {
}
