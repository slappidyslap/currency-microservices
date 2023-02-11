package kg.musabaev.currencyrate;


import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;

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
	@XmlJavaTypeAdapter(DoubleTypeXmlAdapter.class)
	private Double value;

	private static class DoubleTypeXmlAdapter extends XmlAdapter<String, Double> {

		@Override
		public Double unmarshal(String v) throws Exception {
			return (Double) NumberFormat.getInstance().parse(v);
		}

		@Override
		public String marshal(Double v) {
			return v.toString();
		}
	}
}
