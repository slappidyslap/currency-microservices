package kg.musabaev.currencyrate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "${app.cbr.url}", name = "cbr-currency-rate")
public interface CbrFeignClient {

	@GetMapping(path = "/daily_utf8.xml", consumes = MediaType.APPLICATION_XML_VALUE)
//	@Cacheable("cbr-currency-rate")
	ResponseEntity<CbrResponse> getAllCbrCurrencyRate();
}
