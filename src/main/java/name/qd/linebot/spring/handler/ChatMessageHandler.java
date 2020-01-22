package name.qd.linebot.spring.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import name.qd.linebot.spring.command.CommandDispatcher;

@LineMessageHandler
public class ChatMessageHandler {
	private final Logger log = LoggerFactory.getLogger(ChatMessageHandler.class);
	
	private CommandDispatcher commandDispatcher;
	
	@Autowired
	public ChatMessageHandler(CommandDispatcher commandDispatcher) {
		this.commandDispatcher = commandDispatcher;
	}

	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
		String text = event.getMessage().getText();
		if(commandDispatcher.isAvailable(text)) {
			// TODO check is vip
			
			commandDispatcher.execute(event);
		} else {
			// TODO log message, maybe is personal message
		}
	}

	@EventMapping
	public void handleDefaultMessageEvent(Event event) {
		log.info("handleDefaultMessageEvent: " + event);
	}
}
