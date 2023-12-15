package student.examples.server;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import student.examples.comm.Action;
import student.examples.comm.SecureIOStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IOHandler extends Thread {

	private static final Logger LOGGER = LogManager.getLogger(IOHandler.class);

	private final ConcurrentHashMap<Socket, Client> clients;

	private final ExecutorService clientPool = Executors.newCachedThreadPool();

	public IOHandler(ConcurrentHashMap<Socket, Client> clients) {
		this.clients = clients;
	}

	@Override
	public void run() {
		while (true) {

			clients.forEach((clientSocket, client) -> {
				clientPool.execute(() -> {

					try {
						SecureIOStream ioStream = new SecureIOStream(
								new BufferedInputStream(clientSocket.getInputStream()),
								new BufferedOutputStream(clientSocket.getOutputStream()), client.getSecretKey());

						Action action;

						while (!clientSocket.isClosed()) {
							action = Action.getByInt(ioStream.receive());
							LOGGER.info("Server received: " + action + " from " + clientSocket.getInetAddress());
							if (action == null) {
								Thread.currentThread().interrupt();
								clientSocket.close();
								clients.remove(clientSocket);
								break;
							} else {
								if (clientSocket.isConnected()) {
									switch (action) {
									case POKE:
										LOGGER.info("Ok is sent to " + clientSocket.getInetAddress());
										ioStream.send(Action.OK.ordinal());
										break;
									default:
										LOGGER.info("Server received something wrong");
										ioStream.send(Action.ERROR.ordinal());
										break;
									}
								}
							}
						}
					} catch (IllegalArgumentException e) {
						LOGGER.error(e.getMessage(), e);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
			});

		}
	}
}
