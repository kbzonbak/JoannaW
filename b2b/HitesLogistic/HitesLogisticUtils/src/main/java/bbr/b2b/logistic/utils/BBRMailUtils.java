package bbr.b2b.logistic.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BBRMailUtils {
	public static synchronized <T> String processHtml(String filename, Map<String, Object> properties, Class<T> getclass) {
		String htmlContent = null;
		InputStream htmlContentStream = null;
		try {
			htmlContentStream = getclass.getClassLoader().getResourceAsStream("bbr/b2b/logistic/html/" + filename);
			Scanner s = new Scanner(htmlContentStream, "utf-8");
			s.useDelimiter("\\A");
			htmlContent = new String(s.hasNext() ? s.next() : "");
			s.close();
			htmlContentStream.close();
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}

		Pattern tag = Pattern.compile("<condition(.*?)>(.*?)</condition>");
		Pattern attribute = Pattern.compile("data='.*'");
		while (Pattern.matches("<condition(.*?)>(.*?)</condition>", htmlContent)) {
			Matcher matcherattr = attribute.matcher(htmlContent);
			Matcher matchall = tag.matcher(htmlContent);
			String keyProperty = matcherattr.group(0);
			if (!properties.containsKey(keyProperty)) {
				htmlContent = htmlContent.replace(matchall.group(0), "");
			} else {
				htmlContent = htmlContent.replace("<condition data='.*'>", "");
				htmlContent = htmlContent.replace("</condition>", "");
			}
		}

		for (String key : properties.keySet()) {
			String value = String.valueOf(properties.get(key));
			htmlContent = htmlContent.replaceAll("\\$\\{" + key + "\\}", value);
		}

		return htmlContent;
	}
}
