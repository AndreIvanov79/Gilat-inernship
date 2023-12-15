package student.examples.server;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import student.examples.comm.Action;
import student.examples.comm.IOStream;
import student.examples.comm.UuidUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class ConnectionHandler extends Thread {

	private static final Logger LOGGER = LogManager.getLogger(ConnectionHandler.class);

	private final ServerSocket serverSocket;

	private final ConcurrentHashMap<Socket, Client> clients;
	Socket clientSocket = null;

	public ConnectionHandler(ServerSocket serverSocket, ConcurrentHashMap<Socket, Client> clients) {
		super();
		this.serverSocket = serverSocket;
		this.clients = clients;
	}

	@Override
	public void run() {

		synchronized (clients) {

			final ExecutorService clientPool = Executors.newFixedThreadPool(10);

			while (!serverSocket.isClosed()) {

				try {
					Future<Client> future = clientPool.submit(() -> {
						clientSocket = serverSocket.accept();
						Client client = connectionAuthorization();
						return (client);
					});
					if (future.get() != null) {
						clients.put(clientSocket, future.get());
						clients.values().stream().forEach(cl -> System.out.println("MAP: "+cl));
					} else {
						clientSocket.close();
					}
				} catch (InterruptedException | ExecutionException e) {
					System.out.println(e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					clients.remove(clientSocket);
				}
			}
		}

	}

	private Client connectionAuthorization() throws IOException, SQLException {

		LOGGER.info("ConnectionAuthorization method started...");
		IOStream ioStream = new IOStream(new BufferedInputStream(clientSocket.getInputStream()),
				new BufferedOutputStream(clientSocket.getOutputStream()));

		UUID clientId = UuidUtils.asUuid(ioStream.receiveBytes());

		if (getClientFromDb(getDBconnection(), clientId) == null) {
//			ioStream.send(Action.SHUTDOWN.ordinal());
//			clientSocket.close();
			
			LOGGER.info("This UUID: " + clientId + " is not found in DB");
			return null;

		} else {
			ioStream.send(Action.OK.ordinal());
		}

		return getClientFromDb(getDBconnection(), clientId);
	}

	public Client getClientFromDb(Connection conn, UUID clientId) throws SQLException {
		Client client = null;
		UUID id;
		SecretKey secretKey;

		String query = "SELECT * FROM clients WHERE clients.id = ?;";

		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setObject(1, clientId);

		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {

			id = (UUID) rs.getObject("id");
			byte[] keyInBytes = hexStringToByteArray(rs.getString("key"));
			secretKey = new SecretKeySpec(keyInBytes, 0, keyInBytes.length, "DES");

			client = new Client(id, secretKey);
		}

		return client;
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public Connection getDBconnection() throws SQLException {
		String url = "jdbc:postgresql://localhost:5436/gg?user=postgres&password=postgres&ssl=false";
		return DriverManager.getConnection(url);
	}
}
