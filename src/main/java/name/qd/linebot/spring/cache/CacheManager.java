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
			for(String command : newCacheResult.keys()) {
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
		return cacheResult.keys();
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
		String jsonString = "{\"lastUpdateTime\": \"1-1\",\"command\": \"1-1\",\"value\": \"1-1\",\"description\": \"1-1\"," + 
				"\"nextCommands\": {\"1-1\": {\"lastUpdateTime\": \"2-1\",\"command\": \"2-1\",\"value\": \"2-1\"," + 
				"\"description\": \"2-1\",\"nextCommands\": {\"2-1\": {\"lastUpdateTime\": \"2-2\",\"command\": \"2-2\"," + 
				"\"value\": \"2-2\",\"description\": \"2-2\",\"nextCommands\": {\"3-1\": {\"lastUpdateTime\": \"3-1\"," + 
				"\"command\": \"3-1\",\"value\": \"3-1\",\"description\": \"3-1\",\"nextCommands\": {}}," + 
				"\"3-2\": {\"lastUpdateTime\": \"3-2\",\"command\": \"3-2\",\"value\": \"3-2\",\"description\": \"3-2\"," + 
				"\"nextCommands\": {}},\"3-3\": {\"lastUpdateTime\": \"3-3\",\"command\": \"3-3\",\"value\": \"3-3\"," + 
				"\"description\": \"3-3\",\"nextCommands\": {}}}},\"2-2\": {\"lastUpdateTime\": \"3-0\",\"command\": \"3-0\"," + 
				"\"value\": \"3-0\",\"description\": \"3-0\",\"nextCommands\": {\"3-1\": {\"lastUpdateTime\": \"3-1\"," + 
				"\"command\": \"3-1\",\"value\": \"3-1\",\"description\": \"3-1\",\"nextCommands\": {}},\"3-2\": {" + 
				"\"lastUpdateTime\": \"3-2\",\"command\": \"3-2\",\"value\": \"3-2\",\"description\": \"3-2\"," + 
				"\"nextCommands\": {}},\"3-3\": {\"lastUpdateTime\": \"3-3\",\"command\": \"3-3\"," + 
				"\"value\": \"3-3\",\"description\": \"3-3\",\"nextCommands\": {}}}}}}}}";
		
		String jsonString2 = "{\"lastUpdateTime\": \"1-1\",\"command\": \"1-1\",\"value\": \"1-1\",\r\n" + 
				"\"description\": \"1-1\",\"nextCommands\": {\"1-1\": {\"lastUpdateTime\": \"2-1\",\r\n" + 
				"\"command\": \"2-1\",\"value\": \"2-1\",\"description\": \"2-1\",\"nextCommands\": {\r\n" + 
				"\"2-1\": {\"lastUpdateTime\": \"2-2\",\"command\": \"2-2\",\"value\": \"2-2\",\r\n" + 
				"\"description\": \"2-2\",\"nextCommands\": {\"aa\": {\"lastUpdateTime\": \"al\",\r\n" + 
				"\"command\": \"ac\",\"value\": \"av\",\"description\": \"ad\",\"nextCommands\": {}\r\n" + 
				"},\"bb\": {\"lastUpdateTime\": \"bl\",\"command\": \"bc\",\"value\": \"bv\",\r\n" + 
				"\"description\": \"bd\",\"nextCommands\": {}},\"cc\": {\"lastUpdateTime\": \"cl\",\r\n" + 
				"\"command\": \"cc\",\"value\": \"cv\",\"description\": \"cd\",\"nextCommands\": {}}}}}}}}";
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			CacheResult cacheResult = objectMapper.readValue(jsonString, CacheResult.class);
			CacheResult newCacheResult = objectMapper.readValue(jsonString2, CacheResult.class);
			
			CacheManager cacheManager = new CacheManager();
			
			cacheManager.updateCacheResult(cacheResult);
			
			
			
			cacheManager.updateCacheResult(newCacheResult);
			
			String[] commands = {"1-1"};
			CacheResult c = cacheManager.getCacheResult(commands);
			
			System.out.println(objectMapper.writeValueAsString(c));
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
