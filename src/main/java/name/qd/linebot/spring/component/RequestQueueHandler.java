package name.qd.linebot.spring.component;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import name.qd.linebot.spring.vo.ClientAction;

@Component
public class RequestQueueHandler {
	private static final Logger log = LoggerFactory.getLogger(RequestQueueHandler.class);
	private final ExecutorService executor = Executors.newSingleThreadExecutor();
	private ClientActionQueue clientActionQueue = new ClientActionQueue();
	
	@Autowired
	private ChipAnalyzeService chipAnalyzeService;
	
	@PostConstruct
	private void init() {
		executor.execute(clientActionQueue);
	}
	
	public void putAction(ClientAction clientAction) {
		clientActionQueue.putQueue(clientAction);
	}
	
	public class ClientActionQueue implements Runnable {
		private final ConcurrentLinkedDeque<ClientAction> queue = new ConcurrentLinkedDeque<>();
		
		public void putQueue(ClientAction clientAction) {
			queue.offer(clientAction);
		}
		
		@Override
		public void run() {
			ClientAction clientAction;
			while (!Thread.currentThread().isInterrupted()) {
				if((clientAction = queue.poll())!=null) {
					if(clientAction.getChipAnalyzers() != null) {
						chipAnalyzeService.getResult(clientAction);
					} else if(clientAction.getTechAnalyzers() != null) {
						
					} else {
						log.error("No analyzer was set.");
					}
				}
			}
		}
	}
}
