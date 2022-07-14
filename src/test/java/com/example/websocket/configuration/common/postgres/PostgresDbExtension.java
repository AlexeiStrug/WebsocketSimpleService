package com.example.websocket.configuration.common.postgres;

import lombok.extern.java.Log;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

@Log
public class PostgresDbExtension implements BeforeAllCallback {

    public static PostgresDbContainer postgresDbContainer;

    @Override
    public void beforeAll(ExtensionContext context) {
        postgresDbContainer = new PostgresDbContainer();
        log.info("Starting PostgresDB Container");
        postgresDbContainer.start();
        System.setProperty("POSTGRES_URI", "jdbc:postgresql://" + postgresDbContainer.getHost() + ":" + postgresDbContainer.getDefaultPort() + "/test");
        log.info("Finished starting PostgresDB Container");
    }
}
