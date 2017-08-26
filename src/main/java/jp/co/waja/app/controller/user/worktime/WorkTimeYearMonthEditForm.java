package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.core.entity.WorkTimeYearMonth;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class WorkTimeYearMonthEditForm implements Serializable {
	private List<WorkTimeEditForm> workTimes;

	public WorkTimeYearMonthEditForm(WorkTimeYearMonth workTimeYearMonth) {
		this.workTimes = workTimeYearMonth.getWorkTimes().stream()
				.map(WorkTimeEditForm::new)
				.collect(Collectors.toList());
	}
}
