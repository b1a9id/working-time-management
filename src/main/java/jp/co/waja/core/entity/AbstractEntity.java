package jp.co.waja.core.entity;

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

	@Column(nullable = false, name = "created_by")
	@CreatedBy
	private String createdBy;

	@Column(nullable = false, name = "created_at")
	@CreatedDate
	private LocalDateTime createdAt;

	@Column(nullable = false, name = "last_modified_by")
	@LastModifiedBy
	private String lastModifiedBy;

	@Column(nullable = false, name = "last_modified_at")
	@LastModifiedDate
	private String lastModifiedAt;

	@Override
	public PK getId() {
		return id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(String lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
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

		return null == this.getId() && this.getId().equals(that.getId());
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
