package name.qd.linebot.spring.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	public static ObjectMapper getObjectMapper() {
		return objectMapper;
	}
}
