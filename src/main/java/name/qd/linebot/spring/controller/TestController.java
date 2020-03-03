package name.qd.linebot.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import name.qd.linebot.spring.service.TestService;

@RestController
@RequestMapping("/test")
public class TestController extends GeneralExceptionController {
	@Autowired
	private TestService testService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<String> getTechCoreUrl() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<String> writeFile(@RequestParam String text) {
		// write file
		if(testService.writeFile(text)) {
			String fileText = testService.readFile();
			return new ResponseEntity<>(fileText, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public ResponseEntity<String> write(@RequestParam String text) {
		// write file
		if(testService.writeFile(text)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "read", method = RequestMethod.GET)
	public ResponseEntity<String> read() {
		String fileText = testService.readFile();
		return new ResponseEntity<>(fileText, HttpStatus.OK);
	}
}
