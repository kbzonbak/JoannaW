package bbr.esb.utils;

import java.security.MessageDigest;

public class PasswordGenerator {

	public static String getAlphaNumericPassword(int n) {
		char[] pw = new char[n];
		int c = 'A';
		int r1 = 0;
		for (int i = 0; i < n; i++) {
			r1 = (int) (Math.random() * 3);
			switch (r1) {
				case 0:
					c = '0' + (int) (Math.random() * 10);
					break;
				case 1:
					c = 'a' + (int) (Math.random() * 26);
					break;
				case 2:
					c = 'A' + (int) (Math.random() * 26);
					break;
			}
			pw[i] = (char) c;
		}
		return new String(pw);
	}

	public static String getEncryptedPassword(String pass) {
		StringBuffer h = null;
		try {
			// Se crea una instancia del algoritmo 'MD5'
			MessageDigest md = MessageDigest.getInstance("MD5"); //$NON-NLS-1$
			// Para obtener el vector resumen invocaremos el método digest():
			byte[] b = md.digest(pass.getBytes());
			int size = b.length;
			h = new StringBuffer(size);
			for (int i = 0; i < size; i++) {
				int u = b[i] & 255; // unsigned conversion
				if (u < 16) {
					h.append("0" + Integer.toHexString(u)); //$NON-NLS-1$
				} else {
					h.append(Integer.toHexString(u));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return h.toString();
	}

	public static String getNumericPassword(int n) {
		char[] pw = new char[n];
		int c = '0';
		for (int i = 0; i < n; i++) {
			c = '0' + (int) (Math.random() * 10);
			pw[i] = (char) c;
		}
		String pass = new String(pw);
		return pass;
	}
}
