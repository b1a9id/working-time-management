package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.Team;
import jp.co.waja.core.model.Role;
import jp.co.waja.core.model.staff.StaffEditRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class StaffEditForm implements Serializable {

	@NotNull
	private Long code;

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
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate enteredDate;

	@NotNull
	private Integer workTime;

	private boolean flextime;

	private boolean telework;

	private boolean disabled;

	@NotNull
	private Role role;

	public StaffEditForm(Staff staff) {
		this.code = staff.getCode();
		this.team = staff.getTeam();
		this.nameLast = staff.getNameLast();
		this.nameFirst = staff.getNameFirst();
		this.nameLastKana = staff.getNameLastKana();
		this.nameFirstKana = staff.getNameFirstKana();
		this.email = staff.getEmail();
		this.gender = staff.getGender();
		this.employmentType = staff.getEmploymentType();
		this.enteredDate = staff.getEnteredDate();
		this.workTime = staff.getWorkTime();
		this.flextime = staff.isFlextime();
		this.telework = staff.isTelework();
		this.disabled = staff.isDisabled();
		this.role = staff.getRole();
	}

	public StaffEditRequest toStaffEditRequest() {
		StaffEditRequest request = new StaffEditRequest();
		request.setCode(getCode());
		request.setTeam(getTeam());
		request.setNameLast(getNameLast());
		request.setNameFirst(getNameFirst());
		request.setNameLastKana(getNameLastKana());
		request.setNameFirstKana(getNameFirstKana());
		request.setEmail(getEmail());
		request.setGender(getGender());
		request.setEmploymentType(getEmploymentType());
		request.setEnteredDate(getEnteredDate());
		request.setWorkTime(getWorkTime());
		request.setFlextime(isFlextime());
		request.setTelework(isTelework());
		request.setDisabled(isDisabled());
		request.setRole(getRole());
		return request;
	}
}
