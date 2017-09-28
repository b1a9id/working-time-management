package jp.co.waja.core.support;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class WorkTimeUtilTest {

	/**
	 * 入っている値と順番を検証
	 */
	@Test
	public void workTimeHour() {
		List<Integer> result = WorkTimeUtil.workTimeHour();
		Assertions.assertThat(result)
				.containsExactly(6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
	}
}
