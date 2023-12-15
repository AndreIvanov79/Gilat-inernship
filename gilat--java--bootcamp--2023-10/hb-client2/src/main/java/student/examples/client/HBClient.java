package student.examples.client;

import java.io.IOException;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.PropertyConfigurator;

public class HBClient extends Thread {

	private static final int PORT = 7777;
	private static final String HOST = "localhost";
	private IOHandler2 ioHandler;
	private final ConnectionHandler2 connectionHandler;
	private SecretKey secretKey = new SecretKeySpec(new byte[]{1, 1, 1, 1, 1, 1, 1, 1}, "DES");
	private UUID clientId = UUID.fromString("1111");

	public HBClient() {

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
		HBClient hbClient = new HBClient();
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