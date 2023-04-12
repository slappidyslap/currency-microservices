package kg.musabaev.currencyrate;

import io.micrometer.core.annotation.Timed;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "${app.cbr.url}", name = "cbr-currency-rate")
public interface CbrFeignClient {

	@Timed("fetching_all_cbr_currency_rate")
	@GetMapping(path = "/daily_utf8.xml", consumes = MediaType.APPLICATION_XML_VALUE)
//	@Cacheable("cbr-currency-rate")
	ResponseEntity<CbrResponse> getAllCbrCurrencyRate();
}
