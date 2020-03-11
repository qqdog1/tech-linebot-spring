package name.qd.linebot.spring.component;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;

import name.qd.linebot.spring.command.Command;
import name.qd.linebot.spring.command.impl.UIDCommand;
import name.qd.linebot.spring.service.CacheManageService;
import name.qd.linebot.spring.util.LineUtils;
import name.qd.linebot.spring.vo.CacheResult;

@Component
public class CommandDispatcher {
	private static Map<String, Command> mapSystemCommand = new HashMap<>();
	
	@Autowired
	private CacheManageService cacheManageService;
	
	@Autowired
	private LineMessagingClient lineMessagingClient;
	
	@PostConstruct
	private void init() {
		Command uidCommand = new UIDCommand(lineMessagingClient);
		mapSystemCommand.put(uidCommand.getCommandKey(), uidCommand);
	}
	
	public void execute(MessageEvent<TextMessageContent> event) {
		String text = event.getMessage().getText();
		String[] commands = text.split(" ");
		if(commands[0].equals("help")) {
			listAllCommand(event);
		} else if(cacheManageService.isCommandAvailable(commands[0])) {
			getCache(event.getReplyToken(), commands);
		} else if(mapSystemCommand.containsKey(commands[0])) {
			mapSystemCommand.get(commands[0]).executeCommand(event);
		}
	}
	
	private void getCache(String replyToken, String ... commands) {
		CacheResult cacheResult = cacheManageService.getCacheResult(commands);
		StringBuilder sb = new StringBuilder();
		sb.append(cacheResult.getLastUpdateTime()).append("\n");
		sb.append(cacheResult.getValue());
		
		LineUtils.sendReply(lineMessagingClient, replyToken, sb.toString());
	}
	
	private void listAllCommand(MessageEvent<TextMessageContent> event) {
		StringBuilder sb = new StringBuilder();
		// implement command
		for(String command : mapSystemCommand.keySet()) {
			sb.append(command).append(": ");
			sb.append(mapSystemCommand.get(command).getDescription());
			sb.append("\n");
		}
		// cache
		sb.append(cacheManageService.getAllDescription());
		
		LineUtils.sendReply(lineMessagingClient, event, sb.toString());
	}
}
