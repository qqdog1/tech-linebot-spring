package name.qd.linebot.spring.vo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CacheResult {
	private String lastUpdateTime;
	private String command;
	private String value;
	private String description;
	private Map<String, CacheResult> mapCacheResult = new HashMap<>();
	
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<String> getKeys() {
		return mapCacheResult.keySet();
	}
	public boolean remove(String command) {
		if(mapCacheResult.containsKey(command)) {
			mapCacheResult.remove(command);
			return true;
		}
		return false;
	}
	public boolean isCommandAvailable(String command) {
		return mapCacheResult.containsKey(command);
	}
	public CacheResult getCacheResult(String command) {
		return mapCacheResult.get(command);
	}
	public void setCacheResult(Map<String, CacheResult> map) {
		this.mapCacheResult = map;
	}
}
