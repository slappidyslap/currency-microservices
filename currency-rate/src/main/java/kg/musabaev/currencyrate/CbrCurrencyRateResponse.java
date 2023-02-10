package kg.musabaev.currencyrate;

import jakarta.xml.bind.annotation.*;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;


@Data
@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
@Builder
public class CbrCurrencyRateResponse {

	@Singular
	@XmlElement(name = "Valute")
	private List<CurrencyRate> currencyRates;
}
