package jp.co.waja.core.support;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by uchitate on 2017/06/26.
 */
public class WorkTimeUtil {

	public static List<Integer> workTimeHour() {
		return IntStream.rangeClosed(6, 22).boxed().collect(Collectors.toList());
	}

	public static List<Integer> workTimeMinute() {
		return Arrays.asList(0, 15, 45);
	}
}
