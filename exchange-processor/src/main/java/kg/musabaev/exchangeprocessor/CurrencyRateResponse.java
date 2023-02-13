package kg.musabaev.exchangeprocessor;

public record CurrencyRateResponse(
		String id,
		int numCode,
		String charCode,
		int nominal,
		String name,
		Double value
) {
}
