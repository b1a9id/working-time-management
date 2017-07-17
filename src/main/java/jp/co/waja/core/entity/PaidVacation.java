package jp.co.waja.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "paid_vacation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaidVacation extends AbstractEntity<Long> implements Serializable {

	@ManyToOne
	@JoinColumn(name = "staff_id")
	private Staff staff;

	@Column(nullable = false)
	private Integer days;

	@Column(name = "provide_date", nullable = false)
	private LocalDate provideDate;

	@Column(name = "disappear_date", nullable = false)
	private LocalDate disappearDate;
}
