package name.qd.linebot.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController extends GeneralExceptionController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<String> getTechCoreUrl() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
