package name.qd.linebot.spring.command.impl;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;

import name.qd.linebot.spring.command.Command;

public class AnalysisCommand extends Command {

	public AnalysisCommand(LineMessagingClient lineMessagingClient) {
		super(lineMessagingClient);
	}

	@Override
	public String getCommandKey() {
		return "analysis";
	}

	@Override
	public void executeCommand(MessageEvent<TextMessageContent> event) {
		
	}

	@Override
	public String getDescription() {
		return "取得分析數據";
	}
}
