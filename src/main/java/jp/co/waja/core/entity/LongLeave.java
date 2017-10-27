package jp.co.waja.core.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "long_leave")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class LongLeave extends AbstractEntity<Long> implements Serializable {

	public enum Type {
		MATERNITY,
		CHILD
	}

	@ManyToOne
	@JoinColumn(nullable = false, name = "staff_id")
	private Staff staff;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Type type;

	@Column(name = "start_at", nullable = false)
	private LocalDate startAt;

	@Column(name = "end_at")
	private LocalDate endAt;

	private String remarks;
}
