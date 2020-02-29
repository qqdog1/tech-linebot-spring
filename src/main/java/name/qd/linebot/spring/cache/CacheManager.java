package name.qd.linebot.spring.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import name.qd.linebot.spring.vo.CacheResult;

public class CacheManager {
	private static CacheManager instance = new CacheManager();
	private Map<String, CacheResult> map = new HashMap<>();
	
	public static CacheManager getInstance() {
		return instance;
	}
	
	private CacheManager() {
	}
	
	public CacheResult getCacheResult(String command) {
		if(!map.containsKey(command)) {
			map.put(command, new CacheResult());
		}
		return map.get(command);
	}
	
	public boolean isCommandAvailable(String command) {
		return map.containsKey(command);
	}
	
	public String getAllDescription() {
		StringBuilder sb = new StringBuilder();
		for(String command : map.keySet()) {
			sb.append(command).append(": ").append(getDescription(command)).append("\n");
		}
		return sb.toString();
	}
	
	public String getDescription(String command) {
		return map.get(command).getDescription();
	}
	
	public Set<String> getAllCommand() {
		return map.keySet();
	}
	
	public void clear() {
		map.clear();
	}
}
