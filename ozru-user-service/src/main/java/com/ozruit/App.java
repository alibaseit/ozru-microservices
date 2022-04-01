package com.ozruit;


import com.ozruit.services.UserServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ali Dogan
 */
public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private static final int PORT = 8080;

    private final Server server;

    public App() {
        this.server = ServerBuilder.forPort(PORT)
                .addService(new UserServiceImpl())
                .build();
    }

    private void startServer() {
        try {
            LOGGER.info("Starting App");
            server.start();
            LOGGER.info("App is running, listens on port: {}", PORT);
            server.awaitTermination();
            LOGGER.info("App stopped");
        } catch (Exception e) {
            LOGGER.error("Server Start exception ", e);
        }
    }

    public static void main(String[] args) {
        final App app = new App();

        app.startServer();
    }

}
