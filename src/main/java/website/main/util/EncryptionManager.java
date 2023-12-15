package website.main.util;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptionManager {
	
	public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Verify a password against its hash
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

}
