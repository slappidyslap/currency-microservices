package kg.musabaev.exchangeprocessor;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"name", "ownerId"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id", nullable = false)
	Long id;

	@Column(nullable = false)
	String name;

	@Column(nullable = false)
	double totalMoney;

	@Column(nullable = false)
	@Positive
	Long ownerId;
}
