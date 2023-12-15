package student.examples.server;

import java.util.Arrays;
import java.util.UUID;

import javax.crypto.SecretKey;

public class Client {
	
	private UUID clientId;
	
	private SecretKey secretKey;
	
	public Client(UUID clientId, SecretKey secretKey) {
		this.clientId = clientId;
		this.secretKey = secretKey;
	}
	
	public Client() {}

	public UUID getClientId() {
		return clientId;
	}

	public void setClientId(UUID clientId) {
		this.clientId = clientId;
	}

	public SecretKey getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(SecretKey secretKey) {
		this.secretKey = secretKey;
	}

	@Override
	public String toString() {
		return "Client [clientId=" + clientId + ", secretKey=" 
				+ Arrays.toString(secretKey.getEncoded()) + "]";
	}
	
	

}
