package jp.co.waja.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicHoliday extends AbstractEntity<Long> implements Serializable {
	private int year;

	private int month;

	private int dayOfMonth;
}
