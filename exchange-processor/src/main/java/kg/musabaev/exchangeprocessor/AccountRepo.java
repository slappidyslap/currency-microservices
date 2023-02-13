package kg.musabaev.exchangeprocessor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

	List<Account> findAllByOwnerId(Long ownerId);

	boolean existsByOwnerIdAndName(Long ownerId, String name);

	@Query("SELECT a.name FROM Account a WHERE a.id = ?1")
	@Cacheable("account-name-by-id")
	Optional<String> findAccountNameById(long accountId);

	@Query(value = "UPDATE accounts SET total_money = total_money + ?2 WHERE account_id = ?1", nativeQuery = true)
	@Modifying
	void updateTotalMoneyByOwnerIdAndAccountName(long accountId, double money);
}
