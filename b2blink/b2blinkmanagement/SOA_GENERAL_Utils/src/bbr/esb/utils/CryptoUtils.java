package bbr.esb.utils;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import bbr.common.adtclasses.exceptions.OperationFailedException;

public class CryptoUtils {

	public static String encodeHmacSHA256(String key, String data) throws OperationFailedException {
		javax.crypto.spec.SecretKeySpec keySpec = new javax.crypto.spec.SecretKeySpec(key.getBytes(), "HmacSHA256");
		javax.crypto.Mac mac;
		try {
			mac = javax.crypto.Mac.getInstance("HmacSHA256");
			mac.init(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new OperationFailedException("Error generando firma");
		} catch (InvalidKeyException e) {
			throw new OperationFailedException("Error generando firma");
		}
		String sessionKey = Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes()));
		return sessionKey;
	}

	public static String encodeSHA(String message) throws OperationFailedException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.reset();
			md.update(message.getBytes());
			byte[] digest = md.digest();
			String sessionKey = Base64.getEncoder().encodeToString(digest);
			return sessionKey;
		} catch (NoSuchAlgorithmException e) {
			throw new OperationFailedException("Error generando firma");
		}
	}

	public static String encodeMD5(String message) throws OperationFailedException {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(message.getBytes());
			byte[] digest = md.digest();
			String sessionKey = Base64.getEncoder().encodeToString(digest);
			return sessionKey;
		} catch (NoSuchAlgorithmException e) {
			throw new OperationFailedException("Error generando firma");
		}
	}

	public static String generateSessionKey() throws OperationFailedException {
		Double random1 = new Double(Math.random());
		Double random2 = new Double(Math.random());
		Double random3 = new Double(Math.random());
		String mensaje = (random1.toString() + random2.toString() + random3.toString()).replace('.', '0');
		String enc = CryptoUtils.encodeSHA(mensaje);
		// System.out.println("semilla: " + mensaje + " session: " + enc);
		return enc;
	}

	public static void main(String[] args) {
		try {
			Double random1 = new Double(Math.random());
			Double random2 = new Double(Math.random());
			String mensaje = (random1.toString() + random2.toString()).replace('.', '0');
			System.out.println("Mensaje: " + mensaje);
			for (int i = 0; i < 10; i++) {
				String enc = CryptoUtils.encodeSHA(mensaje);
				System.out.println(enc);
			}
		} catch (OperationFailedException e) {
		}
	}

}
