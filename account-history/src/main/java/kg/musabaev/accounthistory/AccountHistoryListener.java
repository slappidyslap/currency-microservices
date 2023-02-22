package kg.musabaev.accounthistory;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class AccountHistoryListener {

	AccountEventRepo accountEventRepo;

	@RabbitListener(queues = RabbitMqConfig.QUEUE_ACCOUNT_CREATE_EVENT)
	void onAnyAccountOperation(AccountCreateEvent event) {
		accountEventRepo.saveAccountCreateEvent(event.getUserId(), event.getAccountName());
	}

	@RabbitListener(queues = RabbitMqConfig.QUEUE_ACCOUNT_MONEY_PUT_EVENT)
	void onPuttingMoney(AccountMoneyPutEvent event) {
		accountEventRepo.saveAccountMoneyPutEvent(event.getUserId(), event.getAccountId(), event.getMoney());
	}
}
