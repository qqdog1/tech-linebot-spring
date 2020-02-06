package name.qd.linebot.spring.command.impl;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;

import name.qd.linebot.spring.command.Command;

public class UIDCommand extends Command {

	public UIDCommand(LineMessagingClient lineMessagingClient) {
		super(lineMessagingClient);
	}

	@Override
	public String getCommandKey() {
		return "uid";
	}

	@Override
	public void executeCommand(MessageEvent<TextMessageContent> event) {
		sendReply(event, event.getSource().getUserId());
	}

	@Override
	public String getDescription() {
		return "取得UserId";
	}
}
