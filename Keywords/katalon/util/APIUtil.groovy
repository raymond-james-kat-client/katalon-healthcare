package katalon.util

public class APIUtil {
	public String constructPayLoadFromMap(Map map) {

		String payload = '{'

		for (def key : map.keySet()) {
			String value = map.get(key)

			payload += "\"$key\":\"$value\","
		}

		payload = payload.substring(0, payload.length() - 1)

		payload += '}'
	}
}
