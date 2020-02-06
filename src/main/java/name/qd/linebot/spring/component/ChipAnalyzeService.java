package name.qd.linebot.spring.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.linecorp.bot.client.LineMessagingClient;

import name.qd.analysis.Constants.Action;
import name.qd.analysis.Constants.Exchange;
import name.qd.analysis.chip.analyzer.ChipAnalyzerManager;
import name.qd.analysis.dataSource.DataSource;
import name.qd.analysis.dataSource.DataSourceFactory;
import name.qd.linebot.spring.cache.CacheManager;
import name.qd.linebot.spring.command.impl.AnalysisCommand;
import name.qd.linebot.spring.util.DateUtils;
import name.qd.linebot.spring.util.LineUtils;
import name.qd.linebot.spring.vo.ClientAction;

@Component
public class ChipAnalyzeService {
	private ChipAnalyzerManager manager;
	private CacheManager cacheManager;
	
	@Autowired
	private LineMessagingClient lineMessagingClient;
	
	@Value("${file.cache.path}")
	private String cachePath;
	@Value("${twse.data.path}")
	private String twseDataPath;
	
	@PostConstruct
	private void init() {
		manager = new ChipAnalyzerManager(cachePath);
		cacheManager = CacheManager.getInstance();
	}
	
	public void getResult(ClientAction clientAction) {
		Exchange exchange = Exchange.valueOf(clientAction.getMarket());
		DataSource dataSource = DataSourceFactory.getInstance().getDataSource(exchange, twseDataPath);
		List<Date> lstFromTo = DateUtils.getFromToByDays(twseDataPath, clientAction.getMarket(), clientAction.getDays());
		// analyze
		List<List<String>> lst = manager.getAnalysisResult(dataSource, clientAction.getChipAnalyzers(), clientAction.getBranch(), clientAction.getProduct(), lstFromTo.get(0), lstFromTo.get(1), clientAction.getTradeCost(), false, clientAction.getInputs());
		// parse
		List<String> lstParseString = parseToShowString(clientAction, lst);
		// cache
		putCache(clientAction, lstParseString);
		// send reply
		sendReply(clientAction);
	}
	
	private void putCache(ClientAction clientAction, List<String> lst) {
		switch(clientAction.getChipAnalyzers()) {
		case BEST_BRANCH_BUY_SELL:
			cacheManager.setAnalysisResult(AnalysisCommand.BEST_BUY, lst.get(0));
			cacheManager.setAnalysisResult(AnalysisCommand.BEST_SELL, lst.get(1));
			break;
		default:
			break;
		}
	}
	
	private List<String> parseToShowString(ClientAction clientAction, List<List<String>> lstResult) {
		switch(clientAction.getChipAnalyzers()) {
		case BEST_BRANCH_BUY_SELL:
			return parseBestBranchBuySell(lstResult);
		default:
			break;
		}
		return null;
	}
	
	private void sendReply(ClientAction clientAction) {
		String text;
		switch(clientAction.getChipAnalyzers()) {
		case BEST_BRANCH_BUY_SELL:
			switch(clientAction.getSide()) {
			case BUY:
				text = cacheManager.getAnalysisResult(AnalysisCommand.BEST_BUY);
				LineUtils.sendReply(lineMessagingClient, clientAction.getReplyToken(), text);
				break;
			case SELL:
				text = cacheManager.getAnalysisResult(AnalysisCommand.BEST_SELL);
				LineUtils.sendReply(lineMessagingClient, clientAction.getReplyToken(), text);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
	}
	
	private List<String> parseBestBranchBuySell(List<List<String>> lstResult) {
		List<String> lst = new ArrayList<>();
		StringBuilder sbBuy = new StringBuilder();
		StringBuilder sbSell = new StringBuilder();
		
		for(List<String> lstBS : lstResult) {
			if(Action.BUY.name().equals(lstBS.get(2))) {
				sbBuy.append(lstBS.get(0)).append(":").append(lstBS.get(1)).append("\n");
			} else if(Action.SELL.name().equals(lstBS.get(2))) {
				sbBuy.append(lstBS.get(0)).append(":").append(lstBS.get(1)).append("\n");
			}
		}
		
		lst.add(sbBuy.toString());
		lst.add(sbSell.toString());
		return lst;
	}
}
