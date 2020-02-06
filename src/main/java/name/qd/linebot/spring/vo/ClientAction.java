package name.qd.linebot.spring.vo;

import name.qd.analysis.Constants.Action;
import name.qd.analysis.chip.ChipAnalyzers;
import name.qd.analysis.tech.TechAnalyzers;

public class ClientAction {
	private String market;
	private TechAnalyzers techAnalyzers;
	private ChipAnalyzers chipAnalyzers;
	private int days;
	private String branch;
	private String product;
	private double tradeCost;
	private Action side;
	private String[] inputs;
	private String replyToken;
	
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public TechAnalyzers getTechAnalyzers() {
		return techAnalyzers;
	}
	public void setTechAnalyzers(TechAnalyzers techAnalyzers) {
		this.techAnalyzers = techAnalyzers;
	}
	public ChipAnalyzers getChipAnalyzers() {
		return chipAnalyzers;
	}
	public void setChipAnalyzers(ChipAnalyzers chipAnalyzers) {
		this.chipAnalyzers = chipAnalyzers;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public double getTradeCost() {
		return tradeCost;
	}
	public void setTradeCost(double tradeCost) {
		this.tradeCost = tradeCost;
	}
	public Action getSide() {
		return side;
	}
	public void setSide(Action side) {
		this.side = side;
	}
	public String[] getInputs() {
		return inputs;
	}
	public void setInputs(String ... inputs) {
		this.inputs = inputs;
	}
	public String getReplyToken() {
		return replyToken;
	}
	public void setReplyToken(String replyToken) {
		this.replyToken = replyToken;
	}
}
