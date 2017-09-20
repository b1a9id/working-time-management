package jp.co.waja.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "work_time_year_month")
@Getter
@Setter
@NoArgsConstructor
public class WorkTimeYearMonth extends AbstractEntity<Long> implements Serializable {

	public WorkTimeYearMonth(Staff staff, Integer workYearMonth) {
		this.staff = staff;
		this.workYearMonth = workYearMonth;
	}

	@ManyToOne
	@JoinColumn(name = "staff_id", nullable = false)
	private Staff staff;

	@Column(name = "work_year_month", nullable = false)
	private Integer workYearMonth;

	@Column(name = "completed_by")
	private String completedBy;

	@Column(name = "completed_at")
	private LocalDateTime completedAt;

	@Column(name = "approved1_by")
	private String approved1By;

	@Column(name = "approved1_at")
	private LocalDateTime approved1At;

	@Column(name = "approved2_by")
	private String approved2By;

	@Column(name = "approved2_at")
	private LocalDateTime approved2At;

	@Embedded
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "work_time",
			joinColumns = @JoinColumn(name = "work_time_year_month_id")
	)
	private List<WorkTime> workTimes;

	public long countByWorkType(WorkTime.WorkType workType) {
		List<WorkTime> workTimes = getWorkTimes();
		return workTimes.stream()
				.filter(workTime -> workTime.getWorkType().equals(workType))
				.count();
	}
}
