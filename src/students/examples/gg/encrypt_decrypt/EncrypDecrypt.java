package students.examples.gg.encrypt_decrypt;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;

public class EncrypDecrypt {

	public void generateAndSaveKey() throws Exception {

		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(512);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();

		FileOutputStream publicOut = new FileOutputStream("./resources/publicKey");
		FileOutputStream privateOut = new FileOutputStream("./resources/privateKey");
		try (ObjectOutputStream pubObjOut = new ObjectOutputStream(publicOut);
				ObjectOutputStream prObjOut = new ObjectOutputStream(privateOut)) {
			

			pubObjOut.writeObject(publicKey);
			prObjOut.writeObject(privateKey);
		}

	}

	public void encryptMessageAndSave(String message) throws Exception {
		System.out.println("RECEIVED MESSAGE: " + message+"\n");

		FileInputStream publicOut = new FileInputStream("./resources/publicKey");
		try (ObjectInputStream pubObjOut = new ObjectInputStream(publicOut);
				FileOutputStream messageOut = new FileOutputStream("./resources/userMessage")) {
			

			PublicKey publicKey = (PublicKey) pubObjOut.readObject();

			Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			cipher.update(message.getBytes());

			byte[] ciphertext = cipher.doFinal();
			messageOut.write(ciphertext);
			System.out.println("message: " + new String(message));
			System.out.println("ciphertext: " + new String(ciphertext, "UTF8"));
		}

	}

	public void decryptAndPrint() throws Exception {
		try (FileInputStream fromFile = new FileInputStream("./resources/userMessage");
				FileInputStream publicOut = new FileInputStream("./resources/privateKey");
				ObjectInputStream pubObjOut = new ObjectInputStream(publicOut)) {
			byte[] ciphertextFromFile = fromFile.readAllBytes();

			
				PrivateKey privateKey = (PrivateKey) pubObjOut.readObject();

				Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
				cipher.init(Cipher.DECRYPT_MODE, privateKey);

				cipher.update(ciphertextFromFile);

				byte[] decrypted = cipher.doFinal();
				System.out.println("decrypted: " + new String(decrypted, "UTF8"));
		}
	}

}
