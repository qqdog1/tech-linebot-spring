package name.qd.linebot.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class DataInputService {
	@Autowired
	private CacheManageService cacheManageService;

	public void updateCache(String data) throws JsonProcessingException {
		try {
			cacheManageService.updateCacheResult(data);
		} catch (JsonProcessingException e) {
			throw e;
		}
	}

	public void removeCache(String... commands) {
		cacheManageService.remove(commands);
	}
}
