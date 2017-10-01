package jp.co.waja.core.repository.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.Staff_;
import jp.co.waja.core.entity.Team;
import jp.co.waja.core.model.staff.StaffSearchRequest;
import jp.co.waja.core.service.team.TeamService;
import jp.co.waja.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

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

		List<Staff> staffs = entityManager.createQuery(query).getResultList();
		int start = pageable.getOffset();
		int end = start + pageable.getPageSize() > staffs.size() ? staffs.size() : start + pageable.getPageSize();
		if (start > end) {
			throw new NotFoundException();
		}
		return new PageImpl<>(staffs.subList(start, end), pageable, staffs.size());
	}
}
