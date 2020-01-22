package name.qd.linebot.spring.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@RestController
@LineMessageHandler
@RequestMapping("/")
public class ChatMessageHandler {
	private final Logger log = LoggerFactory.getLogger(ChatMessageHandler.class);

	@EventMapping
	public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
		final String originalMessageText = event.getMessage().getText();
		return new TextMessage(originalMessageText);
	}

	@EventMapping
	public void handleDefaultMessageEvent(Event event) {
		log.info("handleDefaultMessageEvent: " + event);
	}
}
