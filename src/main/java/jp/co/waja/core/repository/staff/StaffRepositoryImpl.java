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

		if (request.getTelework() != null) {
			Predicate truePredicate = builder.isTrue(root.get(Staff_.telework));
			Predicate falsePredicate = builder.isFalse(root.get(Staff_.telework));
			Predicate teleworkPredicate = request.getTelework() ? truePredicate : builder.or(truePredicate, falsePredicate);
			where.add(teleworkPredicate);
		}

		Predicate truePredicate = builder.isTrue(root.get(Staff_.disabled));
		Predicate falsePredicate = builder.isFalse(root.get(Staff_.disabled));
		Predicate disabledPredicate = request.getDisabled() == null || !request.getDisabled() ? falsePredicate : truePredicate;
		where.add(disabledPredicate);

		query.where(where.toArray(new Predicate[where.size()]));
		return entityManager.createQuery(query).getResultList();
	}
}
