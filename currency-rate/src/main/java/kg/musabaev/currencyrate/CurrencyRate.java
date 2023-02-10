package kg.musabaev.currencyrate;


import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@XmlRootElement(name = "Valute")
@XmlAccessorType(XmlAccessType.FIELD)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRate {

	@XmlAttribute(name = "ID")
	private String id;

	@XmlElement(name = "NumCode")
	private int numCode;

	@XmlElement(name = "CharCode")
	private String charCode;

	@XmlElement(name = "Nominal")
	private int nominal;

	@XmlElement(name = "Name")
	private String name;

	@XmlElement(name = "Value")
	private double value;
}
