package name.qd.linebot.spring.component;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import name.qd.linebot.spring.command.Command;
import name.qd.linebot.spring.command.impl.AnalysisCommand;
import name.qd.linebot.spring.command.impl.UIDCommand;

@Component
public class CommandDispatcher {
	private static Map<String, Command> map = new HashMap<>();
	
	@Autowired
	private LineMessagingClient lineMessagingClient;
	
	@Autowired
	private RequestQueueHandler requestQueueHandler;
	
	@PostConstruct
	private void init() {
		Command uidCommand = new UIDCommand(lineMessagingClient);
		map.put(uidCommand.getCommandKey(), uidCommand);
		
		Command analysisCommand = new AnalysisCommand(lineMessagingClient, requestQueueHandler);
		map.put(analysisCommand.getCommandKey(), analysisCommand);
	}

	public boolean isAvailable(String text) {
		String command = text.split(" ")[0];
		if(command.equals("list")) {
			return true;
		}
		return map.containsKey(text.split(" ")[0]);
	}
	
	public void execute(MessageEvent<TextMessageContent> event) {
		String text = event.getMessage().getText();
		String command = text.split(" ")[0];
		if(command.equals("list")) {
			listAllCommand(event);
		} else {
			map.get(command).executeCommand(event);
		}
	}
	
	private void listAllCommand(MessageEvent<TextMessageContent> event) {
		StringBuilder sb = new StringBuilder();
		for(String command : map.keySet()) {
			sb.append(command).append(": ");
			sb.append(map.get(command).getDescription());
			sb.append("\n");
		}

		String replyToken = event.getReplyToken();
		Message message = new TextMessage(sb.toString());
		ReplyMessage replyMessage = new ReplyMessage(replyToken, message);
		lineMessagingClient.replyMessage(replyMessage);
	}
}
