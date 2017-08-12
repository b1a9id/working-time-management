package jp.co.waja.core.model.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffCreateRequest implements Serializable {

	private Team team;

	private String nameLast;

	private String nameFirst;

	private String nameLastKana;

	private String nameFirstKana;

	private String email;

	private Staff.Gender gender;

	private Staff.EmploymentType employmentType;

	private LocalDate enteredDate;

	private Boolean telework;

	private String password;
}
