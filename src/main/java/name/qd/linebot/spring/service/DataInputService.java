package name.qd.linebot.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import name.qd.linebot.spring.Constants.InputType;
import name.qd.linebot.spring.cache.CacheManager;
import name.qd.linebot.spring.util.JsonUtils;

@Service
public class DataInputService {
	private final Logger log = LoggerFactory.getLogger(DataInputService.class);
	private ObjectMapper objectMapper = JsonUtils.getObjectMapper();
	private CacheManager cacheManager = CacheManager.getInstance();
	
	public void updateCache(String value) {
		try {
			JsonNode node = objectMapper.readTree(value);
			int type = node.get("type").asInt();
			InputType inputType = InputType.getByType(type);
			switch(inputType) {
			case BEST_BUY_SELL:
				processBestBuySell(value);
				break;
			}
		} catch (JsonProcessingException e) {
			log.error("Parse json failed. {}", value);
		}
	}
	
	private void processBestBuySell(String value) {
		
	}
}
