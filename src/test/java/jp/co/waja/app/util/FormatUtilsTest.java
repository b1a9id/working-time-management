package jp.co.waja.app.util;

import jp.co.waja.core.entity.LongLeave;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

class FormatUtilsTest {

	@Nested
	class LongLeaveTest {

		Arguments arguments;

		String expected;

		@BeforeEach
		void beforeEach(RepetitionInfo repetitionInfo) {
			int currentRepetition =  repetitionInfo.getCurrentRepetition();

			if (currentRepetition == 1) {
				arguments = Arguments.of(LocalDate.of(2017, 12, 31), LongLeave.Type.CHILD);
				expected = "2017/12/31まで育休です。";
			}
			if (currentRepetition == 2) {
				arguments = Arguments.of(null, LongLeave.Type.MATERNITY);
				expected = "産休です。";
			}

		}

		@RepeatedTest(2)
		void endAtMessageNull() {
			List<Object> args = Arrays.asList(arguments.get());
			LocalDate entAt = (LocalDate) args.get(0);
			LongLeave.Type type = (LongLeave.Type) args.get(1);

			String result = FormatUtils.longLeave(entAt, type);
			Assertions.assertEquals(expected, result);
		}
	}
}
