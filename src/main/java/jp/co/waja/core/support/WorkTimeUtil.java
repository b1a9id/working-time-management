package jp.co.waja.core.support;

import java.util.List;
import java.util.stream.*;

/**
 * Created by uchitate on 2017/06/26.
 */
public class WorkTimeUtil {

	public static List<Integer> timePerOne() {
		return IntStream.rangeClosed(0, 24).boxed().collect(Collectors.toList());
	}
}
