package name.qd.linebot.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
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
			CacheResult cacheResult = objectMapper.readValue(data, CacheResult.class);
			cacheManager.updateCacheResult(cacheResult);
		} catch (JsonProcessingException e) {
			log.error("Parse json string to CacheResult object failed. {}", data);
			throw e;
		}
	}

	public void removeCache(String... commands) {
		cacheManager.remove(commands);
	}
}
