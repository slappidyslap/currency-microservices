package kg.musabaev.currencyrate;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;


@Data
@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
public class CbrCurrencyRateResponse {

	@XmlElement(name = "Valute")
	private List<CurrencyRate> currencyRates;
}
