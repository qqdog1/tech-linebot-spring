package name.qd.linebot.spring.cache;

import java.util.HashMap;
import java.util.Set;

import name.qd.linebot.spring.vo.CacheResult;

public class CacheManager {
	private static CacheManager instance = new CacheManager();
	private CacheResult rootCacheResult;
	
	public static CacheManager getInstance() {
		return instance;
	}
	
	private CacheManager() {
		initCache();
	}
	
	private void initCache() {
		rootCacheResult = new CacheResult();
		rootCacheResult.setCacheResult(new HashMap<>());
		rootCacheResult.setCommand("");
		rootCacheResult.setDescription("Root");
		rootCacheResult.setValue("");
	}
	
	public CacheResult getCacheResult(CacheResult cacheResult, String command) {
		return cacheResult.getCacheResult(command);
	}
	
	public CacheResult getCacheResult(String ... commands) {
		CacheResult cacheResult = rootCacheResult;
		for(String command : commands) {
			cacheResult = getCacheResult(rootCacheResult, command);
			if(cacheResult == null) {
				break;
			}
		}
		return cacheResult;
	}
	
	public String getAllDescription() {
		StringBuilder sb = new StringBuilder();
		Set<String> commands = getAllCommand();
		for(String command : commands) {
			sb.append(command).append(": ").append(getDescription(rootCacheResult, command)).append("\n");
		}
		return sb.toString();
	}
	
	public String getDescription(CacheResult cacheResult, String command) {
		return cacheResult.getCacheResult(command).getDescription();
	}
	
	public Set<String> getAllCommand() {
		return rootCacheResult.getKeys();
	}
	
	public boolean isCommandAvailable(String command) {
		return isCommandAvailable(rootCacheResult, command);
	}

	public boolean isCommandAvailable(CacheResult cacheResult, String command) {
		return cacheResult.isCommandAvailable(command);
	}
	
	public boolean remove(String ... commands) {
		int size = commands.length;
		
		CacheResult cacheResult = rootCacheResult;
		for(int i = 0 ; i < size-2 ; i++) {
			cacheResult = getCacheResult(cacheResult, commands[i]);
			if(cacheResult == null) {
				return false;
			}
		}
		return cacheResult.remove(commands[size-1]);
	}
	
	public void remove(CacheResult cacheResult, String command) {
		cacheResult.remove(command);
	}
	
	public void clear() {
		initCache();
	}
}
