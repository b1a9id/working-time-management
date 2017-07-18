package jp.co.waja.domain.repository.team;

import jp.co.waja.core.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
	Team findOneById(Long id);
}