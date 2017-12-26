package jp.co.waja.core.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.data.annotation.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;

import javax.persistence.*;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public class AbstractEntity<PK extends Serializable> implements Persistable<PK>, Comparable<AbstractEntity<PK>> {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private PK id;

	@Column(name = "created_by")
	@CreatedBy
	@Getter
	@Setter
	private String createdBy;

	@Column(name = "created_at")
	@CreatedDate
	@Getter
	@Setter
	private LocalDateTime createdAt;

	@Column(name = "last_modified_by")
	@LastModifiedBy
	@Getter
	@Setter
	private String lastModifiedBy;

	@Column(name = "last_modified_at")
	@LastModifiedDate
	@Getter
	@Setter
	private LocalDateTime lastModifiedAt;

	@Override
	public PK getId() {
		return id;
	}

	protected void setId(final PK id) {
		this.id = id;
	}

	@Transient
	public boolean isNew() {
		return null == getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!getClass().equals(ClassUtils.getUserClass(obj))) {
			return false;
		}

		AbstractEntity<?> that = (AbstractEntity<?>) obj;

		return null != this.getId() && this.getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		int hashCode = 17;
		hashCode += null == getId() ? 0 : getId().hashCode() * 31;
		return hashCode;
	}

	@Override
	public int compareTo(AbstractEntity<PK> o) {
		if (getId() == null) {
			return 1;
		}
		return new CompareToBuilder().append(getId(), o.getId()).toComparison();
	}
}
