package kg.musabaev.exchangeprocessor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-rate")
public interface CurrencyRateFeignClient {

	@GetMapping("/rates/{charCode}")
	ResponseEntity<CurrencyRateResponse> getCurrencyRateByCharCode(@PathVariable String charCode);
}
