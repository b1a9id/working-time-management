package jp.co.waja.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "work_time")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkTime extends AbstractEntity<Long> implements Serializable {

	enum workType {
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

	@ManyToOne
	@JoinColumn(name = "staff_id")
	private Staff staff;

	@Column(nullable = false)
	private LocalDateTime date;

	@Column(name = "work_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private workType workType;

	@Column(name = "start_at")
	private LocalTime startAt;

	@Column(name = "end_at")
	private LocalTime endAt;

	@Column(name = "rest_time", nullable = false)
	private Integer restTime;

	private String remarks;
}
