package student.examples.comm;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecureIOStream extends IOStream {
	private final Logger LOGGER = LogManager.getLogger(SecureIOStream.class);
	private Cipher encrypt;
	private Cipher decrypt;
	private final SecretKey secretKey;
	public static final String DES_CTR = "DES/CTR/NoPadding";

	public SecureIOStream(BufferedInputStream bIn, BufferedOutputStream bOut, SecretKey secretKey) {
		super(bIn, bOut);
		try {
			encrypt = Cipher.getInstance(DES_CTR);
			decrypt = Cipher.getInstance(DES_CTR);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		this.secretKey = secretKey;
	}

	@Override
	public void send(int value) throws IOException {
		byte[] buffer = encrypt(new byte[] { (byte) value });
		byte[] length = ByteBuffer.allocate(4).putInt(buffer.length).array();

		bOut.write(length);
		bOut.write(buffer);
		bOut.flush();
	}

	@Override
	public int receive() {
		try {
			DataInputStream dataInputStream = new DataInputStream(bIn);
			int length = dataInputStream.readInt();
			byte[] buffer = decrypt(dataInputStream.readNBytes(length));
			return buffer[0];
		} catch (IOException e) {
			return -1;
		}
	}

	private IvParameterSpec generateIv() {
		byte[] ivBytes = new byte[8];
		new SecureRandom().nextBytes(ivBytes);
		return new IvParameterSpec(ivBytes);
	}

	public byte[] encrypt(byte[] data) {
		IvParameterSpec ivSpec = generateIv();
		try {
			encrypt.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
			byte[] encrypted = encrypt.doFinal(data);
			return combineIvAndEncryptedData(ivSpec.getIV(), encrypted);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
				| BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] decrypt(byte[] ivAndData) {
		IvParameterSpec ivSpec = extractIvFromData(ivAndData);
		byte[] data = extractDataWithoutIv(ivAndData);
		try {
			decrypt.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
			return decrypt.doFinal(data);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
				| BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	private byte[] combineIvAndEncryptedData(byte[] iv, byte[] encrypted) {
		byte[] combined = new byte[iv.length + encrypted.length];
		System.arraycopy(iv, 0, combined, 0, iv.length);
		System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);
		return combined;
	}

	private IvParameterSpec extractIvFromData(byte[] combined) {
		byte[] iv = new byte[8];
		System.arraycopy(combined, 0, iv, 0, iv.length);
		return new IvParameterSpec(iv);
	}

	private byte[] extractDataWithoutIv(byte[] combined) {
		byte[] data = new byte[combined.length - 8];
		System.arraycopy(combined, 8, data, 0, data.length);
		return data;
	}
}
