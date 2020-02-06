package name.qd.linebot.spring.command.impl;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;

import name.qd.analysis.Constants.Action;
import name.qd.analysis.chip.ChipAnalyzers;
import name.qd.linebot.spring.cache.CacheManager;
import name.qd.linebot.spring.command.Command;
import name.qd.linebot.spring.component.RequestQueueHandler;
import name.qd.linebot.spring.vo.ClientAction;

public class AnalysisCommand extends Command {
	private CacheManager cacheManager = CacheManager.getInstance();
	public static String BEST_BUY = "bestBuy";
	public static String BEST_SELL = "bestSell";
	
	private RequestQueueHandler requestQueueHandler;
	
	public AnalysisCommand(LineMessagingClient lineMessagingClient, RequestQueueHandler requestQueueHandler) {
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
		String result = cacheManager.getAnalysisResult(BEST_BUY);
		
		if(result == null) {
			ClientAction clientAction = new ClientAction();
			clientAction.setChipAnalyzers(ChipAnalyzers.BEST_BRANCH_BUY_SELL);
			clientAction.setDays(20);
			clientAction.setMarket("TWSE");
			clientAction.setSide(Action.SELL);
			clientAction.setTradeCost(3000000);
			clientAction.setReplyToken(event.getReplyToken());
			requestQueueHandler.putAction(clientAction);
		} else {
			sendReply(event, result);
		}
	}
	
	private void getBestSell(MessageEvent<TextMessageContent> event) {
		String result = cacheManager.getAnalysisResult(BEST_SELL);
		
		if(result == null) {
			ClientAction clientAction = new ClientAction();
			clientAction.setChipAnalyzers(ChipAnalyzers.BEST_BRANCH_BUY_SELL);
			clientAction.setDays(20);
			clientAction.setMarket("TWSE");
			clientAction.setSide(Action.BUY);
			clientAction.setTradeCost(3000000);
			clientAction.setReplyToken(event.getReplyToken());
			requestQueueHandler.putAction(clientAction);
		} else {
			sendReply(event, result);
		}
	}
	
	private void getHelp(MessageEvent<TextMessageContent> event) {
		StringBuilder sb = new StringBuilder();
		sb.append("get bestbuy, 取得近20日前10名分公司，今日買進超過300萬\n");
		sb.append("get bestsell, 取得近20日前10名分公司，今日賣出超過300萬\n");
		
		sendReply(event, sb.toString());
	}

	@Override
	public String getDescription() {
		return "取得分析數據";
	}
}
