package com.example.websocket.configuration.common.kafka;

import lombok.extern.java.Log;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

@Log
public class KafkaExtension implements BeforeAllCallback {

    public static KafkaContainer kafkaContainer;
    public static ZookeeperContainer zookeeperContainer;

    @Override
    public void beforeAll(ExtensionContext context) {
        zookeeperContainer = new ZookeeperContainer();
        log.info("Starting Zookeeper Container");
        zookeeperContainer.start();

        kafkaContainer = new KafkaContainer(zookeeperContainer);
        log.info("Starting Kafka Container");
        kafkaContainer.start();

        System.setProperty("KAFKA_URI", kafkaContainer.getHost() + ":" + kafkaContainer.getDefaultPort());
        log.info("Finished starting Kafka Container");
    }
}
