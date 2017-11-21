package jp.co.waja.core.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.*;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class WorkTime {

	public enum WorkTypeGroup {
		NORMAL, PAID_VACATION, PAID_VACATION_AFTER, ABSENCE, NORMAL_VACATION
	}

	public enum WorkType {
		NORMAL(BigDecimal.valueOf(1)),
		LEGAL_VACATION(BigDecimal.valueOf(1)),
		FULL_PAID_VACATION(BigDecimal.valueOf(1)),
		HALF_PAID_VACATION(BigDecimal.valueOf(0.5)),
		FULL_PAID_VACATION_AFTER(BigDecimal.valueOf(1)),
		HALF_PAID_VACATION_AFTER(BigDecimal.valueOf(0.5)),
		ABSENCE(BigDecimal.valueOf(1)),
		HALF_ABSENCE(BigDecimal.valueOf(0.5)),
		COMPENSATORY_VACATION(BigDecimal.valueOf(1)),
		SPECIAL_VACATION(BigDecimal.valueOf(1));

		private final BigDecimal day;

		private WorkType(final BigDecimal day) {
			this.day = day;
		}

		public BigDecimal getDay() {
			return this.day;
		}
	}

	public WorkTime(LocalDate date, WorkType workType) {
		this.date = date;
		this.workType = workType;
	}

	@Column(nullable = false)
	private LocalDate date;

	@Column(name = "work_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private WorkType workType;

	@Column(name = "train_delay")
	private Boolean trainDelay;

	@Column(name = "start_at")
	private LocalTime startAt;

	@Column(name = "end_at")
	private LocalTime endAt;

	@Column(name = "rest_time")
	private Integer restTime;

	private String remarks;

	public WorkTypeGroup getWorkTypeGroup() {
		if (workType == WorkType.NORMAL) {
			return WorkTypeGroup.NORMAL;
		}
		if (isPaidVacation()) {
			return WorkTypeGroup.PAID_VACATION;
		}
		if (isPaidVacationAfter()) {
			return WorkTypeGroup.PAID_VACATION_AFTER;
		}
		if (isAbsence()) {
			return WorkTypeGroup.ABSENCE;
		}
		return WorkTypeGroup.NORMAL_VACATION;
	}

	public boolean isPaidVacation() {
		return workType == WorkType.FULL_PAID_VACATION || workType == WorkType.HALF_PAID_VACATION;
	}

	public boolean isPaidVacationAfter() {
		return workType == WorkType.FULL_PAID_VACATION_AFTER || workType == WorkType.HALF_PAID_VACATION_AFTER;
	}

	public boolean isAbsence() {
		return workType == WorkType.ABSENCE || workType == WorkType.HALF_ABSENCE;
	}
}
