package bbr.esb.web.resources;

import java.io.InputStream;
import java.util.Properties;

import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;

import bbr.esb.web.constants.BbrAppConstants;

public class ResourceUtil {

	private static ResourceUtil instance;

	private ResourceUtil() {

	}

	public static ResourceUtil getInstance() {
		if (instance != null)
			return instance;
		else {
			instance = new ResourceUtil();
			return instance;
		}
	}

	public String getPropertyValue(String resourceName, String propertyName) {
		String result = "";

		Properties properties = getInstance().getProperties(resourceName);
		if (properties != null) {
			result = properties.getProperty(propertyName);
		}

		return result;
	}

	public Properties getProperties(String resourceName) {
		Properties result = null;
		InputStream inputStream;
		try {
			inputStream = getInstance().getClass().getClassLoader().getResourceAsStream(resourceName);

			if (inputStream != null) {
				result = new Properties();
				result.load(inputStream);
				inputStream.close();
			}
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}

		return result;
	}

	public Resource getResourceFromThemeResource(String name) {
		String res_logo = getPropertyValue(BbrAppConstants.RES_B2B_PAGES, name);
		Resource res = new ThemeResource(res_logo);
		return res;
	}

	public Image getImageFromThemeResource(String name) {
		Resource res = getResourceFromThemeResource(name);
		Image image = new Image(null, res);
		return image;
	}

}
