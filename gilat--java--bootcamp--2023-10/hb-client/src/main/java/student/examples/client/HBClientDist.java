package student.examples.client;

import java.io.IOException;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class HBClientDist extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(HBClientDist.class);
	private static final int PORT = 7777;
	private static final String HOST = "localhost";
	private IOHandler ioHandler;
	private final ConnectionHandler connectionHandler;
	private SecretKey secretKey = new SecretKeySpec(new byte[] { 56, 4, 16, 26, -74, 84, -89, 115 }, "DES");
	private UUID clientId = UUID.fromString("42a035bc-a6fa-4acd-b977-f2c14078ba41");

	public HBClientDist() {
		LOGGER.info("Client No. "+clientId.toString());
		connectionHandler = new ConnectionHandler(HOST, PORT, clientId);

		try {
			if (connectionHandler.isConnectionAuthorized() == true) {
				ioHandler = new IOHandler(connectionHandler, secretKey);
			} else {
				connectionHandler.interrupt();
				connectionHandler.getClientSocket().close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j2.properties");
		HBClientDist hbClient = new HBClientDist();
		hbClient.connectionHandler.start();
		hbClient.ioHandler.start();
		try {
			hbClient.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}