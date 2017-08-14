package jp.co.waja.core.model.staff;

import jp.co.waja.core.entity.*;
import jp.co.waja.core.model.Role;
import lombok.*;

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

	private Role role;
}
