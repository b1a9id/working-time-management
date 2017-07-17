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

	}

	@ManyToOne
	@JoinColumn(name = "staff_id")
	private Staff staff;

	@Column(nullable = false)
	private LocalDateTime date;

	@Column(name = "work_type", nullable = false)
	private workType workType;

	@Column(name = "start_at", nullable = false)
	private LocalTime startAt;

	@Column(name = "end_at", nullable = false)
	private LocalTime endAt;

	@Column(name = "rest_time", nullable = false)
	private Integer restTime;

	private String remarks;
}
