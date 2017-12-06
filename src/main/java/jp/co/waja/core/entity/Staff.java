package jp.co.waja.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jp.co.waja.core.model.Role;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "staff")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Staff extends AbstractEntity<Long> implements Serializable {

	public enum Gender {
		MAN,
		WOMAN
	}

	public enum EmploymentType {
		PERMANENT_STAFF,
		CREW_ONE,
		CREW_TWO,
		CREW_THREE,
		CREW_FOUR,
		CREW_FIVE
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

	@Column(nullable = false, name = "email", unique = true)
	private String email;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(nullable = false, name = "employment_type")
	@Enumerated(EnumType.STRING)
	private EmploymentType employmentType;

	@Column(nullable = false, name = "entered_date")
	private LocalDate enteredDate;

	private Integer workTime;

	private boolean flextime;

	private boolean telework;

	private boolean disabled;

	@Column(nullable = false)
	@JsonIgnore
	private String password;

	@Embedded
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(
			name = "paid_vacation",
			joinColumns = @JoinColumn(name = "staff_id")
	)
	private List<PaidVacation> paidVacations;

	@JoinTable(name = "user_role")
	@Enumerated(EnumType.STRING)
	@Column(name = "role", length = 20, nullable = false)
	private Role role;

	@OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<LongLeave> longLeaves;

	@Embedded
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(
			name = "staff_history",
			joinColumns = @JoinColumn(name = "staff_id")
	)
	private List<History> histories;

	public String getName() {
		return getNameLast() + " " + getNameFirst();
	}

	public String getNameKana() {
		return getNameLastKana() + " " + getNameFirstKana();
	}

	public List<LongLeave> sortByEndAt() {
		return getLongLeaves().stream()
				.sorted(Comparator.comparing(LongLeave::getEndAt))
				.collect(Collectors.toList());
	}
}
