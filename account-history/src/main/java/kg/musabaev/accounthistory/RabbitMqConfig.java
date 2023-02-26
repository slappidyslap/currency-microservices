package kg.musabaev.accounthistory;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

	public static final String QUEUE_ACCOUNT_CREATE_EVENT = "q.account-create-event";
	public static final String QUEUE_ACCOUNT_MONEY_PUT_EVENT = "q.account-money-put-event";

	@Bean
	Queue accountCreateEventQueue() {
		return new Queue(QUEUE_ACCOUNT_CREATE_EVENT);
	}

	@Bean
	Queue accountMoneyPutEventQueue() {
		return new Queue(QUEUE_ACCOUNT_MONEY_PUT_EVENT);
	}

	@Bean
	public Jackson2JsonMessageConverter jsonConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
