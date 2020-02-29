package name.qd.linebot.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import name.qd.linebot.spring.service.DataInputService;

@RestController
@RequestMapping("/data")
public class DataInputController extends GeneralExceptionController {
	@Autowired
	private DataInputService dataInputService;

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void updateCache(@RequestParam String data) throws JsonProcessingException {
		dataInputService.updateCache(data);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.DELETE)
	public void removeCache(@RequestParam String command) {
		dataInputService.removeCache(command);
	}
}
