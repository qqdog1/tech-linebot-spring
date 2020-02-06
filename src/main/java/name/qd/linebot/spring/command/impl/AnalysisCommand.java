package name.qd.linebot.spring.command.impl;

import java.util.ArrayList;
import java.util.List;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;

import name.qd.linebot.spring.cache.CacheManager;
import name.qd.linebot.spring.command.Command;

public class AnalysisCommand extends Command {
	private CacheManager cacheManager = CacheManager.getInstance();
	private String BEST_BUY = "bestBuy";
	private String BEST_SELL = "bestSell";
	
	public AnalysisCommand(LineMessagingClient lineMessagingClient) {
		super(lineMessagingClient);
	}

	@Override
	public String getCommandKey() {
		return "get";
	}

	@Override
	public void executeCommand(MessageEvent<TextMessageContent> event) {
		String text = event.getMessage().getText();
		String[] command = text.split(" ");
		
		switch(command[1]) {
		case "bestbuy":
			getBestBuy(event);
			break;
		case "bestsell":
			getBestSell(event);
		case "help":
			getHelp(event);
			break;
		}
	}
	
	private void getBestBuy(MessageEvent<TextMessageContent> event) {
		String result = cacheManager.getValue(BEST_BUY);
		
		if(result == null) {
			
			
			
			
			cacheManager.setCache(BEST_BUY, result);
		}
	}
	
	private void getBestSell(MessageEvent<TextMessageContent> event) {
		
	}
	
	private void getHelp(MessageEvent<TextMessageContent> event) {
		StringBuilder sb = new StringBuilder();
		sb.append("get bestbuy, 取得近20日前10名分公司買進超過300萬\n");
		
		sendReply(event, sb.toString());
	}

	@Override
	public String getDescription() {
		return "取得分析數據";
	}
}
