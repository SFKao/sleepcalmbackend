package omelcam934.sleepcalmbackend;

import omelcam934.sleepcalmbackend.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class SleepcalmbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SleepcalmbackendApplication.class, args);
	}

}
