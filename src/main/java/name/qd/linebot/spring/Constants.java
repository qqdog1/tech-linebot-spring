package name.qd.linebot.spring;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	
	public enum InputType {
		BEST_BUY_SELL(1),
		;
		
		private int type;
		private static Map<Integer, InputType> map = new HashMap<>();
		
		static {
			for(InputType inputType : InputType.values()) {
				map.put(inputType.getType(), inputType);
			}
		}
		
		public static InputType getByType(int type) {
			return map.get(type);
		}
		
		InputType(int type) {
			this.type = type;
		}
		
		public int getType() {
			return type;
		}
	}
	
	public enum CacheType {
		BEST_BUY(1),
		BEST_SELL(2),
		;
		private int type;
		private static Map<Integer, CacheType> map = new HashMap<>();
		
		static {
			for(CacheType cacheType : CacheType.values()) {
				map.put(cacheType.getType(), cacheType);
			}
		}
		
		public static CacheType getByType(int type) {
			return map.get(type);
		}
		
		CacheType(int type) {
			this.type = type;
		}
		
		public int getType() {
			return type;
		}
	}
}
