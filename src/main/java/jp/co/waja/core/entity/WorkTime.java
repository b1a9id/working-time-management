package jp.co.waja.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class WorkTime {

	public enum WorkType {
		NORMAL(BigDecimal.ZERO),
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
}
