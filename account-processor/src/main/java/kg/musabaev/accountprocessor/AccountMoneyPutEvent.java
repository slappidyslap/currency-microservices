package kg.musabaev.accountprocessor;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountMoneyPutEvent {
	long userId;
	long accountId;
	double money;
}
