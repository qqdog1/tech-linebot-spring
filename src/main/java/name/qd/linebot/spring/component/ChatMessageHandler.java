package name.qd.linebot.spring.component;

import org.springframework.beans.factory.annotation.Autowired;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@LineMessageHandler
public class ChatMessageHandler {
	private CommandDispatcher commandDispatcher;
	
	@Autowired
	public ChatMessageHandler(CommandDispatcher commandDispatcher) {
		this.commandDispatcher = commandDispatcher;
	}

	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
		commandDispatcher.execute(event);
	}
}
