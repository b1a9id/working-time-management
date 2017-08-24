package jp.co.waja.core.repository.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {

	Staff findOneByEmail(String email);

	List<Staff> findAllByTeam(Team team);

	long countByTeam(Team team);
}
