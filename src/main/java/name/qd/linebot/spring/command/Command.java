package name.qd.linebot.spring.command;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;

import name.qd.linebot.spring.util.LineUtils;

public abstract class Command {
	private LineMessagingClient lineMessagingClient;
	
	public Command(LineMessagingClient lineMessagingClient) {
		this.lineMessagingClient = lineMessagingClient;
	}
	
	protected void sendReply(MessageEvent<TextMessageContent> event, String text) {
		LineUtils.sendReply(lineMessagingClient, event, text);
	}
	
	abstract public String getCommandKey();
	abstract public void executeCommand(MessageEvent<TextMessageContent> event);
	abstract public String getDescription();
}
