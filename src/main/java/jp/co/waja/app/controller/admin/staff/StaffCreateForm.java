package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.Team;
import jp.co.waja.core.model.staff.StaffCreateRequest;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class StaffCreateForm implements Serializable {

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
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate enteredDate;

	private Boolean telework;

	@NotNull
	private String password;

	public StaffCreateRequest toStaffCreateRequest() {
		StaffCreateRequest request = new StaffCreateRequest();
		request.setTeam(getTeam());
		request.setNameLast(getNameLast());
		request.setNameFirst(getNameFirst());
		request.setNameLastKana(getNameLastKana());
		request.setNameFirstKana(getNameFirstKana());
		request.setEmail(getEmail());
		request.setGender(getGender());
		request.setEmploymentType(getEmploymentType());
		request.setEnteredDate(getEnteredDate());
		request.setTelework(getTelework());
		request.setPassword(getPassword());
		return request;
	}
}
