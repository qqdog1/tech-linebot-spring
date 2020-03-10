package name.qd.linebot.spring.vo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CacheResult {
	private String lastUpdateTime;
	private String command;
	private String value;
	private String description;
	private Map<String, CacheResult> nextCommands = new HashMap<>();
	
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
	public Set<String> keys() {
		return nextCommands.keySet();
	}
	public Map<String, CacheResult> getNextCommands() {
		return nextCommands;
	}
	public boolean remove(String command) {
		if(nextCommands.containsKey(command)) {
			nextCommands.remove(command);
			return true;
		}
		return false;
	}
	public boolean hasNext() {
		return nextCommands.size() > 0;
	}
	public boolean isCommandAvailable(String command) {
		return nextCommands.containsKey(command);
	}
	public CacheResult getCacheResult(String command) {
		return nextCommands.get(command);
	}
	public void addCacheResult(String command, CacheResult cacheResult) {
		nextCommands.put(command, cacheResult);
	}
}
