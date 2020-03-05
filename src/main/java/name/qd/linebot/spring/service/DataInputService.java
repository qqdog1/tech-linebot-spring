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
//		try {
//			JsonNode node = objectMapper.readTree(data);
//			CacheResult cacheResult = null;
//			for(JsonNode commandNode : node) {
//				String time = commandNode.get("lastUpdate").asText();
//				String description = commandNode.get("description").asText();
//				String command = commandNode.get("command").asText();
//				String value = commandNode.get("value").asText();
//				cacheResult = cacheManager.getCacheResult(command);
//				if(commandNode.has("inner")) {
//					JsonNode innerNode = commandNode.get("inner");
//				}
//				
//				
//				
//				
//				
//				CacheResult cacheResult = cacheManager.getCacheResult(command);
//				
//				cacheResult.setCommand(command);
//				cacheResult.setDescription(description);
//				cacheResult.setLastUpdateTime(time);
//				cacheResult.setValue(value);
//			}
//		} catch (JsonProcessingException e) {
//			log.error("Parse json failed. {}", data, e);
//			throw e;
//		}
	}
	
	private void updateCache(CacheResult cacheResult, JsonNode node) {
		String time = node.get("lastUpdate").asText();
		String description = node.get("description").asText();
		String command = node.get("command").asText();
		String value = node.get("value").asText();
		
		cacheResult.setCommand(command);
		cacheResult.setDescription(description);
		cacheResult.setLastUpdateTime(time);
		cacheResult.setValue(value);
		
		if(node.has("inner")) {
			
		} else {
			
		}
	}
	
	public void removeCache(String ... commands) {
		cacheManager.remove(commands);
	}
}
