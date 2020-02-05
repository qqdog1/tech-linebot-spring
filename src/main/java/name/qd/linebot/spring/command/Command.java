package name.qd.linebot.spring.command;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

public abstract class Command {
	private LineMessagingClient lineMessagingClient;
	
	public Command(LineMessagingClient lineMessagingClient) {
		this.lineMessagingClient = lineMessagingClient;
	}
	
	protected void sendReply(String replyToken, String text) {
		Message message = new TextMessage(text);
		ReplyMessage replyMessage = new ReplyMessage(replyToken, message);
		lineMessagingClient.replyMessage(replyMessage);
	}
	
	abstract public String getCommandKey();
	abstract public void executeCommand(MessageEvent<TextMessageContent> event);
	abstract public String getDescription();
}
