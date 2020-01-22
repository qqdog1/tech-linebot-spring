package name.qd.linebot.spring.command;

public interface Command {
	
	public String getCommandKey();
	public void executeCommand();
}
