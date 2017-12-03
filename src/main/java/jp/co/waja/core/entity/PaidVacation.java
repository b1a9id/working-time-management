package jp.co.waja.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PaidVacation {

	@Column(nullable = false)
	private int days;

	@Column(name = "provide_date", nullable = false)
	private LocalDate provideDate;

	@Column(name = "disappear_date", nullable = false)
	private LocalDate disappearDate;
}
