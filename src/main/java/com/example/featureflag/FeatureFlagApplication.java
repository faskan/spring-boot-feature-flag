package com.example.featureflag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@SpringBootApplication
public class FeatureFlagApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeatureFlagApplication.class, args);
	}

}

@Configuration
class MyConfiguration {

	@Bean
	@ConditionalOnProperty(prefix = "features", name = "message-service", havingValue = "true")
	public MessageService messageService(){
		return new MessageService();
	}
}

@RestController
class MyRestController {
	private MessageService messageService;

	public MyRestController(Optional<MessageService> optionalMessageService) {
		optionalMessageService.ifPresent(service -> this.messageService = service);
	}

	@GetMapping("/hello")
	public String hello() {
		return messageService.getMessage();
	}
}

class MessageService {

	public String getMessage() {
		return "Hello World";
	}
}


