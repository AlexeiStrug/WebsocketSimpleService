package com.example.websocket.configuration.common.kafka;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

public class ZookeeperContainer extends GenericContainer<ZookeeperContainer> {

    public static final String IMAGE_AND_TAG = "confluentinc/cp-kafka:latest";
    public static final int DEFAULT_PORT = 2181;
    public static final Network NETWORK = Network.newNetwork();

    public ZookeeperContainer() {
        super(IMAGE_AND_TAG);
        withNetwork(NETWORK).withExposedPorts(DEFAULT_PORT)
                .withEnv("ALLOW_ANONYMOUS_LOGIN", "yes")
                .withEnv("ZOOKEEPER_CLIENT_PORT",String.valueOf(DEFAULT_PORT))
                .withEnv("ZOOKEEPER_TICK_TIME", "2000");
    }

    public Integer getDefaultPort() {
        return getMappedPort(DEFAULT_PORT);
    }
}
