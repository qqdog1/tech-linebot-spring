package name.qd.linebot.spring.cache;

import java.util.Set;

import name.qd.linebot.spring.vo.CacheResult;

public class CacheManager {
	private static CacheManager instance = new CacheManager();
	private CacheResult cacheResult;
	
	public static CacheManager getInstance() {
		return instance;
	}
	
	private CacheManager() {
	}
	
	public void updateCacheResult(CacheResult cacheResult) {
		this.cacheResult = cacheResult;
	}
	
	public CacheResult getCacheResult(CacheResult cacheResult, String command) {
		return cacheResult.getCacheResult(command);
	}
	
	public CacheResult getCacheResult(String ... commands) {
		for(String command : commands) {
			cacheResult = getCacheResult(cacheResult, command);
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
			sb.append(command).append(": ").append(getDescription(cacheResult, command)).append("\n");
		}
		return sb.toString();
	}
	
	public String getDescription(CacheResult cacheResult, String command) {
		return cacheResult.getCacheResult(command).getDescription();
	}
	
	public Set<String> getAllCommand() {
		return cacheResult.getKeys();
	}
	
	public boolean isCommandAvailable(String command) {
		return isCommandAvailable(cacheResult, command);
	}

	public boolean isCommandAvailable(CacheResult cacheResult, String command) {
		return cacheResult.isCommandAvailable(command);
	}
	
	public boolean remove(String ... commands) {
		int size = commands.length;
		
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
}
