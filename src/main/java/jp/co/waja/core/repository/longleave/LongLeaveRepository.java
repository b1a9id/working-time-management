package jp.co.waja.core.repository.longleave;

import jp.co.waja.core.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface LongLeaveRepository extends JpaRepository<LongLeave, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	LongLeave findOneById(Long id);
}
