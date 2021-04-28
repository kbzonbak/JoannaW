package bbr.esb.web.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoginUtils {

	private static LoginUtils _instance;

	private Properties properties;

	private LoginUtils() {
		setProperties();
	}

	public static LoginUtils getInstance() {
		if (_instance == null)
			_instance = new LoginUtils();

		return _instance;
	}

	private void setProperties() {

		try {
			properties = new Properties();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream stream = loader.getResourceAsStream("users.properties");
			properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean doLogin(String username, String password) {

		boolean authenticated = false;

		if (properties != null && properties.containsKey(username)) {
			String user_pwd = properties.getProperty(username);
			authenticated = password.equals(user_pwd.trim());
		}

		return authenticated;

	}

}
