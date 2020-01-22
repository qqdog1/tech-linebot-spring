package name.qd.linebot.spring.command;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;

public interface Command {
	public String getCommandKey();
	public void executeCommand(MessageEvent<TextMessageContent> event);
}
