package jp.co.waja.core.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.*;
import java.util.List;

@Entity
@Table(name = "work_time_year_month")
@Getter
@Setter
@NoArgsConstructor
public class WorkTimeYearMonth extends AbstractEntity<Long> implements Serializable {

	public WorkTimeYearMonth(Staff staff, YearMonth yearMonth) {
		this.staff = staff;
		this.yearMonth = yearMonth;
	}

	@ManyToOne
	@JoinColumn(name = "staff_id", nullable = false)
	private Staff staff;

	@Column(nullable = false)
	private YearMonth yearMonth;

	@OneToMany(mappedBy = "workTimeYearMonth", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<WorkTime> workTime;
}
