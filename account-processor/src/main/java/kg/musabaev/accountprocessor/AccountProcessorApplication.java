package kg.musabaev.accountprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AccountProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountProcessorApplication.class, args);
	}

}
