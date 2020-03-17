package name.qd.linebot.spring.service;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import name.qd.linebot.spring.util.GoogleDriveUtils;
import name.qd.linebot.spring.util.JsonUtils;
import name.qd.linebot.spring.vo.CacheResult;

@Service
public class CacheManageService {
	private final Logger log = LoggerFactory.getLogger(CacheManageService.class);
	private static String FILE_NAME = "CacheResult.txt";
	private GoogleDriveUtils googleDriveUtils;
	private ObjectMapper objectMapper = JsonUtils.getObjectMapper();
	
	private CacheResult cacheResult;
	
	@Value("${google.drive.folder}")
	private String folderId;
	
	@PostConstruct
	private void initCache() {
		try {
			googleDriveUtils = new GoogleDriveUtils("./config/credentials.json", "linebot");
		} catch (Exception e) {
			log.error("Init google drive utils failed.", e);
		}
		
		loadFromGoogle();
	}
	
	public void updateCacheResult(String data) throws JsonProcessingException {
		try {
			CacheResult cacheResult = objectMapper.readValue(data, CacheResult.class);
			updateCacheResult(cacheResult);
		} catch (JsonProcessingException e) {
			log.error("Parse json string to CacheResult object failed. {}", data, e);
			throw e;
		}
	}
	
	private void updateCacheResult(CacheResult cacheResult) {
		if(this.cacheResult == null) {
			this.cacheResult = cacheResult;
		} else {
			updateCacheResult(this.cacheResult, cacheResult);
		}
		log.info("Cache updated.");
		uploadToGoogle();
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
	
	private void loadFromGoogle() {
		log.info("Init cache from google drive.");
		
		String fileId = googleDriveUtils.getFileId(folderId, FILE_NAME);
		if(fileId == null) {
			log.info("No cache file on google drive.");
		} else {
			String cacheJson = googleDriveUtils.readFile(fileId);
			if(cacheJson == null) {
				log.error("Get null when read file on google drive. fileId:{}", fileId);
			} else {
				try {
					updateCacheResult(cacheJson);
				} catch (JsonProcessingException e) {
					log.error("Load cache from google drive failed.");
				}
			}
		}
	}
	
	private void uploadToGoogle() {
		log.info("Upload cache to google drive.");
		
		try {
			String jsonString = objectMapper.writeValueAsString(cacheResult);
			String fileId = googleDriveUtils.upload(folderId, jsonString, FILE_NAME);
			log.info("Upload data to google drive. fileId:{}", fileId);
		} catch (JsonProcessingException e) {
			log.error("Parse cacheResult to json string failed.", e);
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
		if(commands == null) return "";
		for(String command : commands) {
			sb.append(command).append(": ").append(getDescription(cacheResult, command)).append("\n");
		}
		return sb.toString();
	}
	
	public String getDescription(CacheResult cacheResult, String command) {
		return cacheResult.getCacheResult(command).getDescription();
	}
	
	public Set<String> getAllCommand() {
		if(cacheResult != null) {
			cacheResult.keys();
		}
		return null;
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
