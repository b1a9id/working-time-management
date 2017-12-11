package jp.co.waja.core.repository.staff;

import jp.co.waja.core.entity.*;
import jp.co.waja.core.model.staff.StaffSearchRequest;
import jp.co.waja.core.service.team.TeamService;
import jp.co.waja.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.*;

public class StaffRepositoryImpl implements StaffRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private TeamService teamService;

	@Transactional(readOnly = true)
	@Override
	public Page<Staff> search(StaffSearchRequest request, Pageable pageable) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Staff> query = builder.createQuery(Staff.class);
		Root<Staff> root = query.from(Staff.class);

		List<Predicate> where = new ArrayList<>();
		if (request.getTeamId() != null) {
			Team team = teamService.getTeam(request.getTeamId());
			where.add(builder.equal(root.get(Staff_.team), team));
		}

		if (request.getEmploymentType() != null) {
			where.add(builder.equal(root.get(Staff_.employmentType), request.getEmploymentType()));
		}

		if (request.getRole() != null) {
			where.add(builder.equal(root.get(Staff_.role), request.getRole()));
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

		query.orderBy(builder.asc(root.get(Staff_.code)));
		query.where(where.toArray(new Predicate[where.size()]));

		List<Staff> staffs = entityManager.createQuery(query).getResultList();
		int start = pageable.getOffset();
		int end = start + pageable.getPageSize() > staffs.size() ? staffs.size() : start + pageable.getPageSize();
		if (start > end) {
			throw new NotFoundException();
		}
		return new PageImpl<>(staffs.subList(start, end), pageable, staffs.size());
	}
}
