package jp.co.waja.core.repository.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface StaffRepository extends StaffRepositoryCustom, JpaRepository<Staff, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Staff findOneById(Long id);

	Staff findOneByEmail(String email);

	List<Staff> findAllByDisabled(boolean disabled);

	List<Staff> findAllByTeam(Team team);

	long countByTeam(Team team);
}
