package jp.co.waja.core.model.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffCreateRequest implements Serializable {

	@NotNull
	private Team team;

	@NotNull
	private String nameLast;

	@NotNull
	private String nameFirst;

	@NotNull
	private String nameLastKana;

	@NotNull
	private String nameFirstKana;

	@NotNull
	@Email
	private String email;

	@NotNull
	private Staff.Gender gender;

	@NotNull
	private Staff.EmploymentType employmentType;

	@NotNull
	private LocalDate enteredDate;

	private Boolean telework;

	@NotNull
	private String password;
}
