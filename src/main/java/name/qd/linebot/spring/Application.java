package name.qd.linebot.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class Application {
	public static void main(String[] s) {
		SpringApplication.run(Application.class, s);
	}
	
	@RequestMapping("/")
    @ResponseBody
    String test() {
      return "Hi!";
    }
}
