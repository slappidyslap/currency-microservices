package kg.musabaev.accounthistory;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "account_money_put_events")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountMoneyPutEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_money_put_event_id", nullable = false)
	Long id;

	@Column(nullable = false)
	Long userId;

	@Column(nullable = false)
	Long accountId;

	@Column(nullable = false)
	Double money;

	@Column(nullable = false)
	LocalDateTime executedDate;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		AccountMoneyPutEvent that = (AccountMoneyPutEvent) o;
		return id != null && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
