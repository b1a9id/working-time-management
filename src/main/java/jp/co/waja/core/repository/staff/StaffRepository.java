package jp.co.waja.core.repository.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends StaffRepositoryCustom, JpaRepository<Staff, Long> {

	Staff findOneByEmail(String email);

	List<Staff> findAllByDisabled(boolean disabled);

	List<Staff> findAllByTeam(Team team);

	long countByTeam(Team team);
}
