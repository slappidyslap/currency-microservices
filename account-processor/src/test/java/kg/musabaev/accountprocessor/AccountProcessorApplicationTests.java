package kg.musabaev.accountprocessor;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class AccountProcessorApplicationTests {

	@InjectMocks
	AccountService accountService;

	@Mock
	AccountRepo accountRepo;
	@Mock
	CurrencyRateFeignClient currencyRateFeignClient;
	@Mock
	RabbitTemplate rabbitTemplate;


	@Test
	void shouldCreateAccount() {
		long ownerId = 1L;
		String accountName = "KGS";

		when(accountRepo.existsByOwnerIdAndName(ownerId, accountName)).thenReturn(false);
		when(accountRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
		when(currencyRateFeignClient.getCurrencyRateByCharCode(accountName))
				.thenReturn(getFoundCurrencyRateAsResponseEntity(accountName));

		Account account = accountService.create(ownerId, accountName);

		assertThat(account.getName()).isEqualTo(accountName);
		assertThat(account.getOwnerId()).isEqualTo(ownerId);
		assertThat(account.getTotalMoney()).isEqualTo(0);
	}

	@Test
	void shouldThrowConflict_whenAccountExists() {
		long ownerId = 2L;
		String accountName = "RUB";

		when(accountRepo.existsByOwnerIdAndName(ownerId, accountName)).thenReturn(true);

		assertThatExceptionOfType(ResponseStatusException.class).isThrownBy(() ->
				accountService.create(ownerId, accountName));
	}

	@ParameterizedTest
	@ValueSource(strings = { "", "kg", "ELD" })
	void shouldThrowBadRequest_whenAccountNameInvalid(String inputAccountName) {
		long ownerId = 3L;

		when(accountRepo.existsByOwnerIdAndName(ownerId, inputAccountName)).thenReturn(false);
		lenient().when(currencyRateFeignClient.getCurrencyRateByCharCode(inputAccountName))
				.thenReturn(getFoundCurrencyRateAsResponseEntity("KGS"));

		assertThatExceptionOfType(ResponseStatusException.class).isThrownBy(() ->
				accountService.create(ownerId, inputAccountName));
	}

	@Test
	void shouldPutMoney() {
		long accountId = 3L;
		double money = 12;
		var accountName = "KZT";

		when(accountRepo.existsById(accountId)).thenReturn(true);
		when(accountRepo.findAccountNameById(accountId)).thenReturn(Optional.of(accountName));
		when(currencyRateFeignClient.getCurrencyRateByCharCode(accountName))
				.thenReturn(getFoundCurrencyRateAsResponseEntity(accountName));

		assertThatCode(() -> accountService.putMoney(anyLong(), accountId, money)).doesNotThrowAnyException();
	}

	@Test
	void shouldThrowAccountNotFound_whenPuttingMoney() {
		long accountId = 3L;

		when(accountRepo.existsById(accountId)).thenReturn(false);

		assertThatExceptionOfType(ResponseStatusException.class).isThrownBy(() ->
				accountService.putMoney(anyLong(), accountId, -123.123));
	}

	private ResponseEntity<CurrencyRateResponse> getFoundCurrencyRateAsResponseEntity(String accountName) {
		return ResponseEntity.ok(new CurrencyRateResponse("RFC123", 231, accountName, 1, "Какая та валюта", 32.32));
	}

}
