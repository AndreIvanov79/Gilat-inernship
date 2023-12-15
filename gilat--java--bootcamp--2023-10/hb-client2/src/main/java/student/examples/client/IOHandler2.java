package student.examples.client;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import student.examples.comm.Action;
import student.examples.comm.SecureIOStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.crypto.SecretKey;

public class IOHandler2 extends Thread {

	private static final Logger LOGGER = LogManager.getLogger(IOHandler2.class);
	private final Socket clientSocket;
	private SecretKey secretKey;

	public IOHandler2(ConnectionHandler2 connectionHandler, SecretKey secretKey) {
		this.clientSocket = connectionHandler.getClientSocket();
		this.secretKey = secretKey;
	}

	@Override
	public void run() {
		
		try {
			SecureIOStream ioStream = new SecureIOStream(new BufferedInputStream(clientSocket.getInputStream()),
					new BufferedOutputStream(clientSocket.getOutputStream()), secretKey);

			while (!clientSocket.isClosed()) {
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					LOGGER.error(e.getMessage(), e);
				}
				
				ioStream.send(Action.POKE.ordinal());
				LOGGER.info("Client send POKE");

				Action action = Action.getByInt(ioStream.receive());
				LOGGER.info("Client received " + action);
				

				if (Action.SHUTDOWN == action || action == null) {
					LOGGER.info("Client received shutdown");
					clientSocket.close();
					System.exit(1);
				} 
				
				if (Action.ERROR == action) {
					LOGGER.info("Client received error");
					clientSocket.close();
				}
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			interrupt();
			System.exit(1);
		}
	}
}
