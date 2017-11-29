package jp.co.waja.core.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class History {

	@Column(name = "field_name")
	private String fieldName;

	@Column(name = "before_value")
	private String beforeValue;

	@Column(name = "after_value", nullable = false)
	private String afterValue;

	@Column(name = "updated_by", nullable = false)
	private String updatedBy;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
}
