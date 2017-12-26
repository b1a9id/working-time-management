package jp.co.waja.core.repository.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.Team;
import jp.co.waja.core.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends StaffRepositoryCustom, JpaRepository<Staff, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Staff findOneById(Long id);

	Staff findOneByEmail(String email);

	Optional<Staff> findOneByCode(long code);

	List<Staff> findAllByDisabled(boolean disabled);

	List<Staff> findAllByEmploymentType(Staff.EmploymentType employmentType);

	List<Staff> findAllByTeam(Team team);

	List<Staff> findAllByRoleIn(List<Role> roles);

	long countByTeam(Team team);
}
