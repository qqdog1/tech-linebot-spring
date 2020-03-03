package name.qd.linebot.spring.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestService {
	private final Logger log = LoggerFactory.getLogger(TestService.class);
	private final Path path = Paths.get("./test.txt");
	
	public boolean writeFile(String text) {
		if(Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				log.error("delete file failed.", e);
				return false;
			}
		}
		
		try {
			Files.createFile(path);
		} catch (IOException e) {
			log.error("Create file failed.", e);
			return false;
		}
		
		try {
			Files.write(path, text.getBytes());
		} catch (IOException e) {
			log.error("Write text to file failed.", e);
			return false;
		}
		
		return true;
	}
	
	public String readFile() {
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(path);
		} catch (IOException e) {
			log.error("Read file failed.", e);
			return "";
		}
		
		return new String(bytes);
	}
}
