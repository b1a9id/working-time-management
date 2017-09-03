package jp.co.waja.core.model.worktime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WorkTimeYearMonthEditRequest implements Serializable {
	private Long id;

	private List<WorkTimeEditRequest> workTimes;
}
