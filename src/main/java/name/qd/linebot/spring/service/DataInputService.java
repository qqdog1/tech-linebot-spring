package name.qd.linebot.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import name.qd.linebot.spring.cache.CacheManager;
import name.qd.linebot.spring.util.JsonUtils;
import name.qd.linebot.spring.vo.CacheResult;

@Service
public class DataInputService {
	private final Logger log = LoggerFactory.getLogger(DataInputService.class);
	private ObjectMapper objectMapper = JsonUtils.getObjectMapper();
	private CacheManager cacheManager = CacheManager.getInstance();
	
	public void updateCache(String data) throws JsonProcessingException {
		try {
			JsonNode node = objectMapper.readTree(data);
			
			String time = node.get("lastUpdate").asText();
			String description = node.get("description").asText();
			String command = node.get("command").asText();
			String value = node.get("value").asText();
			
			CacheResult cacheResult = cacheManager.getCacheResult(command);
			
			cacheResult.setCommand(command);
			cacheResult.setDescription(description);
			cacheResult.setLastUpdateTime(time);
			cacheResult.setValue(value);
			
		} catch (JsonProcessingException e) {
			log.error("Parse json failed. {}", data, e);
			throw e;
		}
	}
	
	public void removeCache(String command) {
		cacheManager.remove(command);
	}
}
