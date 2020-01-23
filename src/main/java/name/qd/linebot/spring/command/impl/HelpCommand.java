package name.qd.linebot.spring.command.impl;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;

import name.qd.linebot.spring.command.Command;

public class HelpCommand extends Command {
	private static final String HELP = "輸入 1 取得XXX \n 2 \n 3";
	
	public HelpCommand(LineMessagingClient lineMessagingClient) {
		super(lineMessagingClient);
	}

	@Override
	public String getCommandKey() {
		return "-h";
	}

	@Override
	public void executeCommand(MessageEvent<TextMessageContent> event) {
		String replyToken = event.getReplyToken();
		sendReply(replyToken, HELP);
	}
}
