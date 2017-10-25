package jp.co.waja.core.service.longleave;

import jp.co.waja.core.entity.*;
import jp.co.waja.core.model.staff.LongLeaveCreateRequest;
import jp.co.waja.core.repository.longleave.LongLeaveRepository;
import jp.co.waja.core.repository.staff.StaffRepository;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class LongLeaveService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private LongLeaveRepository longLeaveRepository;

	public LongLeave getLongLeave(long id) {
		return longLeaveRepository.findOne(id);
	}

	@PreAuthorize("hasRole('ADMIN')")
	public List<LongLeave> create(LongLeaveCreateRequest request) {
		List<LongLeave> savedLongLeaves = new ArrayList<>();

		request.getLongLeaveRequests()
				.forEach(
						longLeaveRequest -> {
							LongLeave longLeave = new LongLeave();
							Staff staff = staffRepository.findOneById(longLeaveRequest.getStaffId());
							longLeave.setStaff(staff);
							longLeave.setType(longLeaveRequest.getType());
							longLeave.setStartAt(longLeaveRequest.getStartAt());
							longLeave.setEndAt(longLeaveRequest.getEndAt());
							longLeave.setRemarks(longLeaveRequest.getRemarks());
							savedLongLeaves.add(longLeaveRepository.saveAndFlush(longLeave));
						}
				);
		return savedLongLeaves;
	}

	@PreAuthorize("hasRole('ADMIN')")
	public String delete(LongLeave longLeave) {
		String staffName = longLeave.getStaff().getName();
		longLeaveRepository.delete(longLeave.getId());
		return staffName;
	}
}
