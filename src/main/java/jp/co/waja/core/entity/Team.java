package jp.co.waja.core.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
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
}
