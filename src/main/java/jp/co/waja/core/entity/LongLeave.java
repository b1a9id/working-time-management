package jp.co.waja.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

@Entity
@Table(name = "long_leave")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class LongLeave extends AbstractEntity<Long> implements Serializable {

	public enum Type {
		MATERNITY("産休"),
		CHILD("育休");

		private final String typeName;

		private Type(final String typeName) {
			this.typeName = typeName;
		}

		public String getTypeName() {
			return this.typeName;
		}

		public static Type getType(final String typeName) {
			return Arrays.stream(Type.values())
					.filter(type -> type.getTypeName().equals(typeName))
					.findFirst()
					.orElse(null);
		}
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
