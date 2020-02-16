package name.qd.linebot.spring.service;

import org.springframework.stereotype.Service;

import name.qd.linebot.spring.cache.CacheManager;

@Service
public class DataInputService {
	private CacheManager cacheManager = CacheManager.getInstance();
	
	public void updateCache(String key, String value) {
		cacheManager.put(key, value);
	}
}
