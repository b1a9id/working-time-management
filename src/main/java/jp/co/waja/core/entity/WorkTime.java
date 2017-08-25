package jp.co.waja.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "work_time")
@Getter
@Setter
@NoArgsConstructor
public class WorkTime extends AbstractEntity<Long> implements Serializable {

	public enum WorkType {
		NORMAL,
		LEGAL_VACATION,
		FULL_PAID_VACATION,
		HALF_PAID_VACATION,
		FULL_PAID_VACATION_AFTER,
		HALF_PAID_VACATION_AFTER,
		ABSENCE,
		HALF_ABSENCE,
		COMPENSATORY_VACATION,
		SPECIAL_VACATION
	}

	public WorkTime(Staff staff, LocalDate date, WorkType workType) {
		this.staff = staff;
		this.date = date;
		this.workType = workType;
	}

	@ManyToOne
	@JoinColumn(name = "staff_id", nullable = false)
	private Staff staff;

	@Column(nullable = false)
	private LocalDate date;

	@Column(name = "work_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private WorkType workType;

	@Column(name = "start_at")
	private LocalTime startAt;

	@Column(name = "end_at")
	private LocalTime endAt;

	@Column(name = "rest_time")
	private Integer restTime;

	private String remarks;
}
