package jp.co.waja.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
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

	/**
	 * 入力完了フラグ
	 */
	@Column(name = "complete")
	private boolean complete;

	/**
	 * マネージャ承認フラグ
	 */
	@Column(name = "approve1")
	private boolean approve1;

	/**
	 * MP承認フラグ
	 */
	@Column(name = "approve2")
	private boolean approve2;

	@Embedded
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "work_time",
			joinColumns = @JoinColumn(name = "work_time_year_month_id")
	)
	private List<WorkTime> workTimes;
}
