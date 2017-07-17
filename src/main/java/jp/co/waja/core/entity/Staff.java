package jp.co.waja.core.entity;

import jp.co.waja.core.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SortNatural;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "staff")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Staff extends AbstractEntity<Long> implements Serializable {

	enum EmploymentType {

	}

	@ManyToOne
	@JoinColumn(nullable = false, name = "team_id")
	private Team team;

	@Column(nullable = false, name = "name_last")
	private String nameLast;

	@Column(nullable = false, name = "name_first")
	private String nameFirst;

	@Column(nullable = false, name = "name_last_kana")
	private String nameLastKana;

	@Column(nullable = false, name = "name_first_kana")
	private String nameFirstKana;

	@Column(nullable = false, name = "email")
	@Email
	private String email;

	@Column(nullable = false, name = "employment_type")
	@Enumerated(EnumType.STRING)
	private EmploymentType employmentType;

	@Column(nullable = false, name = "entered_date")
	private LocalDate enteredDate;

	private Boolean telework;

	@Column(nullable = false)
	private String password;

	@ElementCollection
	@SortNatural
	@JoinTable(name = "user_role")
	@Enumerated(EnumType.STRING)
	@Column(name = "role", length = 20, nullable = false)
	private SortedSet<Role> roles = new TreeSet<>();

	@OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PaidVacation> paidVacations;
}
