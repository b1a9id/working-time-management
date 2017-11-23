package jp.co.waja.core.service.longleave;

import jp.co.waja.core.entity.LongLeave;
import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.repository.longleave.LongLeaveRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.MockitoAnnotations.initMocks;

public class LongLeaveServiceTest {

	@InjectMocks
	private LongLeaveService longLeaveService;

	@Mock
	private LongLeaveRepository longLeaveRepository;

	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void getLongLeave() {
		LongLeave longLeave = generateLongLeave(LongLeave.Type.CHILD);
		Mockito.when(longLeaveRepository.findOne(Mockito.anyLong())).thenReturn(longLeave);

		Assertions.assertThat(longLeaveService.getLongLeave(1L))
				.isEqualToComparingFieldByField(longLeave);
	}

	private Staff generateStaff(String nameLast, String nameFirst) {
		Staff staff = new Staff();
		staff.setNameLast(nameLast);
		staff.setNameFirst(nameFirst);
		return staff;
	}

	private LongLeave generateLongLeave(LongLeave.Type type) {
		return generateLongLeave(null, type, null);
	}

	private LongLeave generateLongLeave(Staff staff, LongLeave.Type type, String remarks) {
		LongLeave longLeave = new LongLeave();
		longLeave.setStaff(staff);
		longLeave.setType(type);
		longLeave.setRemarks(remarks);
		return longLeave;
	}

}
