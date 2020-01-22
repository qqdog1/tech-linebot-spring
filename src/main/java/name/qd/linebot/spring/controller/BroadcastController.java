package name.qd.linebot.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.Broadcast;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

@RestController
@RequestMapping("/broadcast")
public class BroadcastController {
	
	@Autowired
	private LineMessagingClient lineMessagingClient;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public void sendBroadcast(@RequestParam String text) {
		Message message = new TextMessage(text);
		Broadcast broadcast = new Broadcast(message);
		lineMessagingClient.broadcast(broadcast);
	}
}
