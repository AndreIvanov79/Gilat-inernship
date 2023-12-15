package student.examples.server;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class HBServer {

    private static final Logger LOGGER = LogManager.getLogger(HBServer.class);
    private final int PORT;
    private final int BACKLOG;
    private final String HOST;
    private final IOHandler ioHandler;
    private ConnectionHandler connectionHandler = null;
    private final ConcurrentHashMap<Socket, Client> clients;
   

    public HBServer(int PORT, String HOST, int BACKLOG) {
        this.PORT = PORT;
        this.HOST = HOST;
        this.BACKLOG = BACKLOG;
        clients = new ConcurrentHashMap<>();
        try {
            connectionHandler = new ConnectionHandler(
                    new ServerSocket(this.PORT, this.BACKLOG, InetAddress.getByName(this.HOST)), clients);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        ioHandler = new IOHandler(clients);
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure("log4j2.properties");
        HBServer hbServer = new HBServer(7777, "localhost", 100);
        hbServer.run();
        LOGGER.info("Server started. Listening on port " + hbServer.PORT);
    }

    public void run() {
        connectionHandler.start();
        ioHandler.start();
    }
}
