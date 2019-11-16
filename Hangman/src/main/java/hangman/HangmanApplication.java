package hangman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EntityScan("hangmanjpa")
@SpringBootApplication
public class HangmanApplication extends SpringBootServletInitializer{
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(HangmanApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(HangmanApplication.class, args);
	}

}
