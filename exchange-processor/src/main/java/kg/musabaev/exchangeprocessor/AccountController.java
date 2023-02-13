package kg.musabaev.exchangeprocessor;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/accounts")
public class AccountController {

	private final AccountService accountService;
	
	@PostMapping(produces = APPLICATION_JSON_VALUE)
	ResponseEntity<Account> createAccount(@PathVariable Long userId, @RequestBody ObjectNode json) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(accountService.create(userId, json.get("name").asText()));
	}

	@GetMapping
	ResponseEntity<List<Account>> getAllAccountsOfUser(@PathVariable Long userId) {
		return ResponseEntity.ok(accountService.getAccountsOfUser(userId));
	}

	@PutMapping("/{accountId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void putMoneyIntoAccount(
			@PathVariable Long userId,
			@PathVariable Long accountId,
			@RequestBody ObjectNode json
	) {
		accountService.putMoney(userId, accountId, json.get("money").asDouble());
	}
}
