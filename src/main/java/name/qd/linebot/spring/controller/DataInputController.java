package name.qd.linebot.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import name.qd.linebot.spring.service.DataInputService;

@RestController
@RequestMapping("/data")
public class DataInputController {
	@Autowired
	private DataInputService dataInputService;

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void sendBroadcast(@RequestParam String value) {
		dataInputService.updateCache(value);
	}
}
