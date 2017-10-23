package jp.co.waja.core.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class LongLeave {

	public enum Type {
		MATERNITY,
		CHILD
	}

	@Column(nullable = false)
	private Type type;

	@Column(name = "start_at", nullable = false)
	private LocalDate startAt;

	@Column(name = "end_at")
	private LocalDate endAt;

	private String remarks;
}
