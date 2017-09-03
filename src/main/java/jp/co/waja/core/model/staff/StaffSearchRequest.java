package jp.co.waja.core.model.staff;

import jp.co.waja.core.entity.Staff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffSearchRequest implements Serializable {

	private Long teamId;

	private Staff.EmploymentType employmentType;

	private LocalDate enteredDate;

	private Boolean telework;

	private Boolean disabled;
}
