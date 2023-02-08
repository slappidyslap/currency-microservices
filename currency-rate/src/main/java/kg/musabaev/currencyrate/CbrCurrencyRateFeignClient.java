package kg.musabaev.currencyrate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "https://www.cbr-xml-daily.ru", name = "cbr-currency-rate")
public interface CbrCurrencyRateFeignClient {

	@GetMapping(path = "/daily_utf8.xml", consumes = MediaType.APPLICATION_XML_VALUE)
//	@Cacheable("cbr-currency-rate")
	CbrCurrencyRateResponse getAllCbrCurrencyRate();
}
