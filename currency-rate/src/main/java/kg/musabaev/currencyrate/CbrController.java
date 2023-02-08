package kg.musabaev.currencyrate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CbrController {

	private final CbrCurrencyRateFeignClient cbrCurrencyRateFeignClient;

	@Autowired
	public CbrController(CbrCurrencyRateFeignClient cbrCurrencyRateFeignClient) {
		this.cbrCurrencyRateFeignClient = cbrCurrencyRateFeignClient;
	}

	@GetMapping(path = "/rates/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
	CurrencyRate getCurrencyRate(@PathVariable String code) {
		return cbrCurrencyRateFeignClient
				.getAllCbrCurrencyRate()
				.getCurrencyRates()
				.stream()
				.filter(c -> c.getCharCode().equals(code))
				.findFirst()
				.orElseThrow(() -> {throw new ResponseStatusException(HttpStatus.NOT_FOUND);});
	}
}
