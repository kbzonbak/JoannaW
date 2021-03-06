package bbr.b2b.logistic.utils;

import java.io.StringReader;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonStructure;

public class JsonParser {
	public HashMap<String, String> JsonToHashMap(String credential, String... keys) throws Exception {
		String keyName = "";
		try {
			HashMap<String, String> data = new HashMap<>();
			JsonReader reader = Json.createReader(new StringReader(credential));
			JsonStructure jsonst = reader.read();
			JsonObject object = (JsonObject) jsonst;
			for (String key : keys) {
				keyName = key;
				if (object.get(key).getValueType().name().equals("STRING")) {
					JsonString value = (JsonString) object.get(key);
					data.put(key, value.getString());
				} else if (object.get(key).getValueType().name().equals("NUMBER")) {
					int value = object.getInt(key);
					data.put(key, String.valueOf(value));
				}

			}
			return data;
		} catch (Exception e) {
			throw new Exception("Error al obtener key: " + keyName + " " + e.getMessage());
		}
	}
}
