package kg.musabaev.accounthistory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountEventRepo {

	@PersistenceContext
	EntityManager em;

	@Transactional
	void saveAccountCreateEvent(long userId, String accountName) {
		em.createNativeQuery("""
						INSERT INTO account_create_events
						(user_id, account_name, executed_date)
						VALUES ( ?, ?, ? )""")
				.setParameter(1, userId)
				.setParameter(2, accountName)
				.setParameter(3, Date.from(Instant.now()), TemporalType.TIMESTAMP)
				.executeUpdate();
	}

	@Transactional
	void saveAccountMoneyPutEvent(long userId, long accountId, double money) {
		em.createNativeQuery("""
						INSERT INTO account_money_put_events
						(user_id, account_id, money, executed_date)
						VALUES ( ?, ?, ?, ? )""")
				.setParameter(1, userId)
				.setParameter(2, accountId)
				.setParameter(3, money)
				.setParameter(4, Date.from(Instant.now()), TemporalType.TIMESTAMP)
				.executeUpdate();
	}

	@Transactional(readOnly = true)
	List<AccountMoneyPutEvent> getAllAccountMoneyPutEvent() {
		var criteriaQuery = em.getCriteriaBuilder().createQuery(AccountMoneyPutEvent.class);
		return em.createQuery(criteriaQuery.select(criteriaQuery.from(AccountMoneyPutEvent.class))).getResultList();
	}

	@Transactional(readOnly = true)
	List<AccountCreateEvent> getAllAccountCreateEvent() {
		var criteriaQuery = em.getCriteriaBuilder().createQuery(AccountCreateEvent.class);
		return em.createQuery(criteriaQuery.select(criteriaQuery.from(AccountCreateEvent.class))).getResultList();
	}
}
