package name.qd.linebot.spring.util;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

public class LineUtils {

	public static void sendReply(LineMessagingClient lineMessagingClient, String replyToken, String text) {
		Message message = new TextMessage(text);
		ReplyMessage replyMessage = new ReplyMessage(replyToken, message);
		lineMessagingClient.replyMessage(replyMessage);
	}
	
	public static void sendReply(LineMessagingClient lineMessagingClient, MessageEvent<TextMessageContent> event, String text) {
		String replyToken = event.getReplyToken();
		Message message = new TextMessage(text);
		ReplyMessage replyMessage = new ReplyMessage(replyToken, message);
		lineMessagingClient.replyMessage(replyMessage);
	}
}
