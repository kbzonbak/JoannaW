package bbr.b2b.portal.api.managers.classes;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.google.gson.Gson;

public class JsonUtils {
	
	public static JsonObject createJsonObject(Properties properties) {
		JsonObject jObject = getJsonObject(properties);
		return jObject;
	}

	public static JsonArray createJsonArray(Properties... properties) {
		JsonArray jArray = getJsonArray(properties);
		return jArray;
	}

	public static JsonObject getMessage(String message) {
		JsonObject body = Json.createReader(new StringReader(message)).readObject();
		return body;
	}

	private static JsonArray getJsonArray(Properties... properties) {

		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

		for (Properties value : properties) {
			arrayBuilder.add(getJsonObject(value));
		}

		JsonArray jArray = (JsonArray) arrayBuilder.build();

		return jArray;
	}

	private static JsonObject getJsonObject(Properties properties) {

		JsonObjectBuilder jBuilder = Json.createObjectBuilder();

		for (Object key : properties.keySet()) {

			String keyStr = (String) key;

			if (properties.get(keyStr) instanceof String) {
				jBuilder.add(keyStr, (String) properties.get(keyStr));
			} else if (properties.get(keyStr) instanceof Long) {
				jBuilder.add(keyStr, (Long) properties.get(keyStr));
			} else if (properties.get(keyStr) instanceof Boolean) {
				jBuilder.add(keyStr, (Boolean) properties.get(keyStr));
			} else if (properties.get(keyStr) instanceof String[]) {

				JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

				for (String value : (String[]) properties.get(keyStr)) {
					arrayBuilder.add(value);
				}

				jBuilder.add(keyStr, arrayBuilder);
			} else if (properties.get(keyStr) instanceof Map<?, ?>) {
				// En caso de que la propiedad sea un Map
				JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
				Map<Object, Object> mapAtt = (Map<Object, Object>) properties.get(keyStr);
				for (Map.Entry<Object, Object> entry : mapAtt.entrySet()) {
					String keyAtt = (String) entry.getKey();
					// Si es un array
					if (entry.getValue().getClass().isArray()) {
						JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
						Object[] valueAtt = (Object[]) entry.getValue();
						for (Object string : valueAtt) {
							arrayBuilder.add(string.toString());
						}
						objectBuilder.add(keyAtt, arrayBuilder);
					} else {
						objectBuilder.add(keyAtt, entry.getValue().toString());
					}
				}
				jBuilder.add(keyStr, objectBuilder);
			}
		}

		JsonObject jObject = jBuilder.build();
		return jObject;
	}

	public static <T> T getObjectFromJson(String json, Class<T> beanClass) {

		Gson gson = new Gson();

		Object result = gson.fromJson(json, beanClass);

		return (T) result;
	}

	public static <T> T getObjectFromJson(Reader reader, Class<T> beanClass) {

		Gson gson = new Gson();

		Object result = gson.fromJson(reader, beanClass);

		return (T) result;
	}
	
	public static <T> T getObjectFromJson(Reader reader, Type beanClass) {

		Gson gson = new Gson();

		Object result = gson.fromJson(reader, beanClass);

		return (T) result;
	}

	public static String getJsonFromObject(Object object, Type beanClass) {

		Gson gson = new Gson();

		String result = gson.toJson(object, beanClass);

		return result;
	}
}
