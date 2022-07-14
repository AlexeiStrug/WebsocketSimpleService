package com.example.websocket.configuration.common.kafka;

import org.jetbrains.annotations.NotNull;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

public class KafkaContainer extends GenericContainer<KafkaContainer> {

    public static final String IMAGE_AND_TAG = "confluentinc/cp-kafka:latest";
    public static final int DEFAULT_PORT = 9092;
    public static final Network NETWORK = Network.newNetwork();

    public KafkaContainer(ZookeeperContainer zookeeperContainer) {
        super(IMAGE_AND_TAG);
        withNetwork(NETWORK)
                .withExposedPorts(DEFAULT_PORT)
                .withEnv("KAFKA_ZOOKEEPER_CONNECT", getZookeeperConnect(zookeeperContainer))
                .withEnv("KAFKA_ADVERTISED_HOST_NAME", "127.0.0.1")
                .withEnv("KAFKA_ADVERTISED_LISTENERS", "=PLAINTEXT://localhost:9092")
                .withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "false")
                .dependsOn(zookeeperContainer);
    }

    @NotNull
    private String getZookeeperConnect(ZookeeperContainer zookeeperContainer) {
        return zookeeperContainer.getHost() + ':' + zookeeperContainer.getDefaultPort();
    }

    public Integer getDefaultPort() {
        return getMappedPort(DEFAULT_PORT);
    }
}
