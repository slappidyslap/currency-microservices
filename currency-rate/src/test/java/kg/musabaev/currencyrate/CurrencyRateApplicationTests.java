package kg.musabaev.currencyrate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyRateApplicationTests {

	@InjectMocks
	CbrController cbrController;

	@Mock
	CbrCurrencyRateFeignClient currencyRateFeignClient;

	@Test
	void shouldFindRate() {
		var currencyRates = getCbrCurrencyRateResponse();

		when(currencyRateFeignClient.getAllCbrCurrencyRate()).thenReturn(currencyRates);

		CurrencyRate kgsRate = cbrController.getCurrencyRate("KGS");

		assertThat(kgsRate).isNotNull();
		assertThat(kgsRate.getValue()).isEqualTo(1.124);
	}

	@Test
	void shouldNotFindRate() {
		var currencyRates = getCbrCurrencyRateResponse();

		when(currencyRateFeignClient.getAllCbrCurrencyRate()).thenReturn(currencyRates);

		assertThatExceptionOfType(ResponseStatusException.class).isThrownBy(() ->
				cbrController.getCurrencyRate("KZT"));
	}

	private CbrCurrencyRateResponse getCbrCurrencyRateResponse() {
		return CbrCurrencyRateResponse.builder()
				.currencyRate(CurrencyRate.builder().charCode("KGS").value(1.124).build())
				.currencyRate(CurrencyRate.builder().charCode("EUR").value(83.421).build())
				.build();
	}

}
