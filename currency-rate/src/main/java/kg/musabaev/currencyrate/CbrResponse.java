package kg.musabaev.currencyrate;

import jakarta.xml.bind.annotation.*;
import lombok.*;

import java.util.List;


@Data
@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CbrResponse {

	@Singular
	@XmlElement(name = "Valute")
	private List<CurrencyRate> currencyRates;
}
