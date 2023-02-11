package kg.musabaev.currencyrate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
public class CbrController {

	private final CbrFeignClient cbrFeignClient;

	@GetMapping(path = "/{charCode}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CurrencyRate> getCurrencyRate(@PathVariable String charCode) {
		CbrResponse body = cbrFeignClient
				.getAllCbrCurrencyRate()
				.getBody();
		return ResponseEntity.ok(Optional.ofNullable(body)
				.map(CbrResponse::getCurrencyRates)
				.flatMap(rates -> rates
						.stream()
						.filter(rate -> rate.getCharCode().equals(charCode))
						.findFirst())
				.orElseThrow(() -> {throw new ResponseStatusException(HttpStatus.NOT_FOUND);}));
	}
}
