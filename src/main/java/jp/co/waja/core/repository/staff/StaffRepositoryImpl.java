package jp.co.waja.core.repository.staff;

import jp.co.waja.core.entity.*;
import jp.co.waja.core.model.staff.StaffSearchRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.*;

public class StaffRepositoryImpl implements StaffRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	@Override
	public List<Staff> search(StaffSearchRequest request) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Staff> query = builder.createQuery(Staff.class);
		Root<Staff> root = query.from(Staff.class);

		List<Predicate> where = new ArrayList<>();
		if (request.getEmploymentType() != null) {
			where.add(builder.equal(root.get(Staff_.employmentType), request.getEmploymentType()));
		}

		Predicate teleworkPredicate = request.isTelework() ? builder.isTrue(root.get(Staff_.telework)) : builder.isFalse(root.get(Staff_.telework));
		where.add(teleworkPredicate);

		Predicate disabledPredicate = request.isDisabled() ? builder.isTrue(root.get(Staff_.disabled)) : builder.isFalse(root.get(Staff_.disabled));
		where.add(disabledPredicate);

		query.where(where.toArray(new Predicate[where.size()]));
		return entityManager.createQuery(query).getResultList();
	}
}
