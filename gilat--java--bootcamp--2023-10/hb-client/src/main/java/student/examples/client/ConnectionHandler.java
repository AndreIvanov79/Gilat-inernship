package student.examples.client;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import student.examples.comm.Action;
import student.examples.comm.IOStream;
import student.examples.comm.UuidUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class ConnectionHandler extends Thread {

	private static final Logger LOGGER = LogManager.getLogger(ConnectionHandler.class);
	private Socket clientSocket;
	private String HOST;
	private int PORT;
	private UUID clientId;

	public ConnectionHandler(String HOST, int PORT, UUID clientId) {
		super();
		try {
			this.HOST = HOST;
			this.PORT = PORT;
			this.clientSocket = new Socket(HOST, PORT);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		this.clientId = clientId;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
				if (!clientSocket.isConnected()) {
					clientSocket = new Socket(HOST, PORT);
				}
			} catch (InterruptedException | IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public synchronized boolean isConnectionAuthorized() throws IOException {

		boolean authorized = false;

		IOStream ioStream = new IOStream(new BufferedInputStream(clientSocket.getInputStream()),
				new BufferedOutputStream(clientSocket.getOutputStream()));

		ioStream.sendBytes(UuidUtils.asBytes(clientId));
		LOGGER.info("Client send UUID");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Action action = Action.getByInt(ioStream.receive());
		LOGGER.info("Client received " + action);
		
		if (action == null) {
			return authorized;
		}

		if (Action.OK == action) {
			authorized = true;
			LOGGER.info("Connection accepted.");
		}
		if (Action.SHUTDOWN == action) {
			clientSocket.close();
			interrupt();
			LOGGER.info("Server sends SHUTDOWN");
		}
		return authorized;
	}
}
