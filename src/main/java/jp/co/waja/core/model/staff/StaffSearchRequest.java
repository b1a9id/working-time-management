package jp.co.waja.core.model.staff;

import jp.co.waja.core.entity.*;
import lombok.*;

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

	private boolean telework;

	private boolean disabled;
}
