package kg.musabaev.accounthistory;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountEventController {

	AccountEventRepo accountEventRepo;

	@GetMapping("/account-create")
	List<AccountCreateEvent> getAllAccountCreateEvent() {
		return accountEventRepo.getAllAccountCreateEvent();
	}

	@GetMapping("/account-money-put")
	List<AccountMoneyPutEvent> getAllAccountMoneyPutEvent() {
		return accountEventRepo.getAllAccountMoneyPutEvent();
	}
}
