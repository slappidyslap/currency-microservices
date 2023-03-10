package kg.musabaev.currencyrate;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class CurrencyRateApplicationTests {

	@InjectMocks
	CbrController cbrController;

	@Mock
	CbrFeignClient cbrFeignClient;

	@Test
	void shouldFindRate() {
		var currencyRates = getCbrCurrencyRateResponse();

		when(cbrFeignClient.getAllCbrCurrencyRate()).thenReturn(ResponseEntity.ok(currencyRates));

		ResponseEntity<CurrencyRate> kgsRate = cbrController.getCurrencyRate("KGS");

		assertThat(kgsRate.getBody()).isNotNull();
		assertThat(kgsRate.getBody().getValue()).isEqualTo(1.124);
	}

	@Test
	void shouldNotFindRate() {
		var currencyRates = getCbrCurrencyRateResponse();

		when(cbrFeignClient.getAllCbrCurrencyRate()).thenReturn(ResponseEntity.ok(currencyRates));

		assertThatExceptionOfType(ResponseStatusException.class).isThrownBy(() ->
				cbrController.getCurrencyRate("KZT"));
	}

	private CbrResponse getCbrCurrencyRateResponse() {
		return CbrResponse.builder()
				.currencyRate(CurrencyRate.builder().charCode("KGS").value(1.124).build())
				.currencyRate(CurrencyRate.builder().charCode("EUR").value(83.421).build())
				.build();
	}

}
