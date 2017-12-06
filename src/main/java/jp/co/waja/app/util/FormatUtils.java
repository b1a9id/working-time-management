package jp.co.waja.app.util;

import jp.co.waja.core.entity.LongLeave;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.nonNull;

public class FormatUtils {
	public static String longLeave(LocalDate endAt, LongLeave.Type type) {
		String endAtMessage = null;
		if (nonNull(endAt)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			endAtMessage = String.format("%sまで", formatter.format(endAt));
		}
		String typeMessage = String.format("%sです。", type.getTypeName());

		return nonNull(endAtMessage) ? endAtMessage + typeMessage : typeMessage;
	}
}
