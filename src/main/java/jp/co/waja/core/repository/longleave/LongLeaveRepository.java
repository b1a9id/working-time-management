package jp.co.waja.core.repository.longleave;

import jp.co.waja.core.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LongLeaveRepository extends JpaRepository<LongLeave, Long> {
}
