package jp.co.waja.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * チーム
 */
@Entity
@Table(name = "team")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Team extends AbstractEntity<Long> implements Serializable {

	@Column(nullable = false, name = "name")
	private String name;

	@Column(nullable = false, name = "short_name")
	private String shortName;

	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Staff> staffs;
}
