package name.qd.linebot.spring.cache;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {
	private static CacheManager instance = new CacheManager();
	private Map<String, String> mapAnalysisResult = new HashMap<>();
	
	public static CacheManager getInstance() {
		return instance;
	}
	
	private CacheManager() {
	}
	
	public void setAnalysisResult(String key, String value) {
		mapAnalysisResult.put(key, value);
	}
	
	public String getAnalysisResult(String key) {
		return mapAnalysisResult.get(key);
	}
	
	public void clear() {
		mapAnalysisResult.clear();
	}
}
