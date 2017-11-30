package jp.co.waja.core.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * チーム
 */
@Entity
@Table(name = "team")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Team extends AbstractEntity<Long> implements Serializable {

	@Column(nullable = false, name = "name")
	private String name;

	@Column(nullable = false, name = "short_name")
	private String shortName;

	@Override
	public String toString() {
		return this.name;
	}
}
