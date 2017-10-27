package jp.co.waja.core.repository.team;

import jp.co.waja.core.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

public interface TeamRepository extends JpaRepository<Team, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Team findOneById(Long id);
}
