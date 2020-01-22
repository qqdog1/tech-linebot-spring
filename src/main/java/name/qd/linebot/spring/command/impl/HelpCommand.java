package name.qd.linebot.spring.command.impl;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import name.qd.linebot.spring.command.Command;

public class HelpCommand implements Command {
	private static final String HELP = "輸入 1 取得XXX \n 2 \n 3";
	
	private LineMessagingClient lineMessagingClient;
	
	public HelpCommand(LineMessagingClient lineMessagingClient) {
		this.lineMessagingClient = lineMessagingClient;
	}

	@Override
	public String getCommandKey() {
		return "-h";
	}

	@Override
	public void executeCommand(MessageEvent<TextMessageContent> event) {
		String replyToken = event.getReplyToken();
		Message message = new TextMessage(HELP);
		ReplyMessage replyMessage = new ReplyMessage(replyToken, message);
		lineMessagingClient.replyMessage(replyMessage);
	}
}
