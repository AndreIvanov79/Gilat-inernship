package student.examples.uservice.api.business.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class UserToken {

	public String createToken(UUID id, String email) throws NoSuchAlgorithmException {
		String tokenString = "";
		String commonString = id.toString()+email;

		byte[] clientToken;
		MessageDigest messageDigest;

		messageDigest = MessageDigest.getInstance("SHA-256");
		clientToken = messageDigest.digest(commonString.getBytes());
		tokenString = Base64.getEncoder().encodeToString(clientToken);

		return tokenString;
	}
}
