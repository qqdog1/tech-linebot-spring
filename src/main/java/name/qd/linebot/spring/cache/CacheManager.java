package name.qd.linebot.spring.cache;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {
	private static CacheManager instance = new CacheManager();
	private Map<Integer, String> mapAnalysisResult = new HashMap<>();
	
	public static CacheManager getInstance() {
		return instance;
	}
	
	private CacheManager() {
	}
	
	public void put(int key, String value) {
		mapAnalysisResult.put(key, value);
	}
	
	public String get(int key) {
		return mapAnalysisResult.get(key);
	}
	
	public void clear() {
		mapAnalysisResult.clear();
	}
}
