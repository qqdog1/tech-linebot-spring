package name.qd.linebot.spring.command;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;

import name.qd.linebot.spring.command.impl.HelpCommand;

@Service
public class CommandDispatcher {
	private static Map<String, Command> map = new HashMap<>();
	
	@Autowired
	private LineMessagingClient lineMessagingClient;
	
	@PostConstruct
	private void init() {
		Command helpCommand = new HelpCommand(lineMessagingClient);
		map.put(helpCommand.getCommandKey(), helpCommand);
	}

	public boolean isAvailable(String text) {
		return map.containsKey(text.split(" ")[0]);
	}
	
	public void execute(MessageEvent<TextMessageContent> event) {
		String text = event.getMessage().getText();
		map.get(text.split(" ")[0]).executeCommand(event);
	}
}
