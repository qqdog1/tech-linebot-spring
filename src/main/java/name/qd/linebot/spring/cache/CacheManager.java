package name.qd.linebot.spring.cache;

import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		if(this.cacheResult == null) {
			this.cacheResult = cacheResult;
		} else {
			updateCacheResult(this.cacheResult, cacheResult);
		}
	}
	
	private void updateCacheResult(CacheResult cacheResult, CacheResult newCacheResult) {
		cacheResult.setCommand(newCacheResult.getCommand());
		cacheResult.setDescription(newCacheResult.getDescription());
		cacheResult.setLastUpdateTime(newCacheResult.getLastUpdateTime());
		cacheResult.setValue(newCacheResult.getValue());
		
		if(newCacheResult.hasNext()) {
			for(String command : newCacheResult.getKeys()) {
				if(!cacheResult.isCommandAvailable(command)) {
					cacheResult.addCacheResult(command, new CacheResult());
				}
				updateCacheResult(cacheResult.getCacheResult(command), newCacheResult.getCacheResult(command));
			}
		}
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

	public static void main(String[] s) {
		xytotal(1,1,3);
		
//		CacheResult cacheResult = createCacheResult(1, 1, 3);
//		
//		ObjectMapper objectMapper = new ObjectMapper();
//		
//		try {
//			System.out.println(objectMapper.writeValueAsString(cacheResult));
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
	}
	
	private static void xytotal(int x, int y, int total) {
		System.out.println(x + ":" + y + ":" + total);
		
		if(x == y && x < total) {
			x++;
			xytotal(x,y,total);
		} else if(x > y && x < total) {
			x++;
			xytotal(x,y,total);
		} else if(x == total && x > y) {
			y++;
			xytotal(x,y,total);
		}
	}
	
	private static CacheResult createCacheResult(int x, int y, int total) {
		System.out.println(x + ":" + y + ":" + total);
		CacheResult cacheResult = new CacheResult();
		String text = x+"-"+y;
		cacheResult.setCommand(text);
		cacheResult.setDescription(text);
		cacheResult.setLastUpdateTime(text);
		cacheResult.setValue(text);
		
		if(x == y && total > x) {
			x++;
			CacheResult nextCacheResult = createCacheResult(x, 1, total);
			cacheResult.addCacheResult(text, nextCacheResult);
		} else if(x > y && total > x) {
			x++;
			CacheResult nextCacheResult = createCacheResult(x, 1, total);
			cacheResult.addCacheResult(text, nextCacheResult);
		} else if(x == total-1) {
			y++;
			CacheResult nextCacheResult = createCacheResult(x, y, total);
			cacheResult.addCacheResult(text, nextCacheResult);
		}
		
		return cacheResult;
	}
}
