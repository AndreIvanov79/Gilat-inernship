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
	private IOHandler2 ioHandler;
	private final ConnectionHandler2 connectionHandler;
	private SecretKey secretKey = new SecretKeySpec(new byte[]{56, -62, 70, -125, -33, -105, -70, 67}, "DES");
	private UUID clientId = UUID.fromString("d995228c-3d89-4749-a5bb-57747fbc11c6");

	public HBClientDist() {
		LOGGER.info("Client No. "+clientId.toString());
		connectionHandler = new ConnectionHandler2(HOST, PORT, clientId);

		try {
			if (connectionHandler.isConnectionAuthorized() == true) {
				ioHandler = new IOHandler2(connectionHandler, secretKey);
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