package com.example.websocket.configuration.common.postgres;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

public class PostgresDbContainer extends GenericContainer<PostgresDbContainer> {

    public static final String IMAGE_AND_TAG = "postgres";
    public static final int DEFAULT_PORT = 5432;
    public static final Network NETWORK = Network.newNetwork();

    public PostgresDbContainer() {
        super(IMAGE_AND_TAG);
        withNetwork(NETWORK).withExposedPorts(DEFAULT_PORT)
                .withEnv("POSTGRES_PASSWORD", "postgres")
                .withEnv("POSTGRES_USER", "postgres")
                .withEnv("POSTGRES_DB", "test");
    }

    public Integer getDefaultPort() {
        return getMappedPort(DEFAULT_PORT);
    }
}
