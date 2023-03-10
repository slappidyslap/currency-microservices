package kg.musabaev.accountprocessor;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AccountService {

	AccountRepo accountRepo;
	CurrencyRateFeignClient currencyRateFeignClient;
	RabbitTemplate rabbitTemplate;

	@Transactional
	public Account create(long ownerId, String name) {
		if (accountRepo.existsByOwnerIdAndName(ownerId, name))
			throw new ResponseStatusException(HttpStatus.CONFLICT);

		if (isInvalidAccountName(name))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

		rabbitTemplate.convertAndSend(
				RabbitMqConfig.QUEUE_ACCOUNT_CREATE_EVENT,
				new AccountCreateEvent(ownerId, name));

		return accountRepo.save(Account.builder()
				.ownerId(ownerId)
				.name(name)
				.totalMoney(0)
				.build());
	}

	@Transactional(readOnly = true)
	public List<Account> getAccountsOfUser(long ownerId) {
		return accountRepo.findAllByOwnerId(ownerId);
	}

	@Transactional
	public void putMoney(long ownerId, long accountId, double money) {
		if (!accountRepo.existsById(accountId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		double actualMoney = accountRepo.findAccountNameById(accountId)
				.map(currencyRateFeignClient::getCurrencyRateByCharCode)
				.map(ResponseEntity::getBody)
				.map(rate -> money / ( rate.value() / rate.nominal() ) )
				.orElseThrow(() -> {throw new ResponseStatusException(HttpStatus.NOT_FOUND); });

		rabbitTemplate.convertAndSend(
				RabbitMqConfig.QUEUE_ACCOUNT_MONEY_PUT_EVENT,
				new AccountMoneyPutEvent(ownerId, accountId, money));

		accountRepo.updateTotalMoneyByOwnerIdAndAccountName(accountId, actualMoney);
	}

	@Cacheable("invalid-account-name")
	public boolean isInvalidAccountName(String name) {
		if (name == null || !name.matches("[A-Z]{3}")) return true;

		ResponseEntity<CurrencyRateResponse> response = currencyRateFeignClient.getCurrencyRateByCharCode(name);
		if (response.getStatusCode().value() == 404 || response.getBody() == null) return true;

		return !response.getBody().charCode().equals(name);
	}
}
