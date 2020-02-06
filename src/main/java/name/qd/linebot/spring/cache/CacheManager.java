package name.qd.linebot.spring.cache;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {
	private static CacheManager instance = new CacheManager();
	private Map<String, String> mapCache = new HashMap<>();
	
	public static CacheManager getInstance() {
		return instance;
	}
	
	private CacheManager() {
	}
	
	public void setCache(String key, String value) {
		mapCache.put(key, value);
	}
	
	public String getValue(String key) {
		return mapCache.get(key);
	}
	
	public void clear() {
		mapCache.clear();
	}
}
