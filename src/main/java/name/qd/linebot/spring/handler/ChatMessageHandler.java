package name.qd.linebot.spring.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@LineMessageHandler
public class ChatMessageHandler {
	private final Logger log = LoggerFactory.getLogger(ChatMessageHandler.class);

	@Autowired
    private LineMessagingClient lineMessagingClient;
	
	@EventMapping
	public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
		lineMessagingClient.replyMessage(new ReplyMessage(event.getReplyToken(), new TextMessage("HIHIHIHIHIHI")));
		log.info("handleTextMessageEvent: " + event);
		final String originalMessageText = event.getMessage().getText();
		return new TextMessage(originalMessageText);
	}

	@EventMapping
	public void handleDefaultMessageEvent(Event event) {
		System.out.println("handleDefaultMessageEvent");
		log.info("handleDefaultMessageEvent: " + event);
	}
}
