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

	public enum WorkTypeGroup {
		NORMAL, PAID_VACATION, PAID_VACATION_AFTER, ABSENCE, NORMAL_VACATION, ILLEGAL_VACATION
	}

	public enum WorkType {
		NORMAL(BigDecimal.valueOf(1), WorkTypeGroup.NORMAL),
		LEGAL_VACATION(BigDecimal.valueOf(1), WorkTypeGroup.NORMAL_VACATION),
		FULL_PAID_VACATION(BigDecimal.valueOf(1), WorkTypeGroup.PAID_VACATION),
		HALF_PAID_VACATION(BigDecimal.valueOf(0.5), WorkTypeGroup.PAID_VACATION),
		FULL_PAID_VACATION_AFTER(BigDecimal.valueOf(1), WorkTypeGroup.PAID_VACATION_AFTER),
		HALF_PAID_VACATION_AFTER(BigDecimal.valueOf(0.5), WorkTypeGroup.PAID_VACATION_AFTER),
		ABSENCE(BigDecimal.valueOf(1), WorkTypeGroup.ABSENCE),
		HALF_ABSENCE(BigDecimal.valueOf(0.5), WorkTypeGroup.ABSENCE),
		COMPENSATORY_VACATION(BigDecimal.valueOf(1), WorkTypeGroup.ILLEGAL_VACATION),
		SPECIAL_VACATION(BigDecimal.valueOf(1), WorkTypeGroup.ILLEGAL_VACATION);

		private final BigDecimal day;
		private final WorkTypeGroup group;

		private WorkType(final BigDecimal day, final WorkTypeGroup group) {
			this.day = day;
			this.group = group;
		}

		public BigDecimal getDay() {
			return this.day;
		}

		public WorkTypeGroup getGroup() {
			return this.group;
		}
	}

	public WorkTime(LocalDate date, WorkType workType) {
		this.date = date;
		this.workType = workType;
	}
}
