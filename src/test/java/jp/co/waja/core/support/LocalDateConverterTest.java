package jp.co.waja.core.support;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

public class LocalDateConverterTest {

	@Test
	public void convertToDatabaseColumnWhenNull() {
		LocalDateConverter converter = new LocalDateConverter();
		Date result = converter.convertToDatabaseColumn(null);
		Assertions.assertThat(result).isNull();
	}

	@Test
	public void convertToDatabaseColumn() {
		LocalDateConverter converter = new LocalDateConverter();
		LocalDate today = LocalDate.now();

		Date result = converter.convertToDatabaseColumn(today);
		Assertions.assertThat(result).isEqualTo(Date.valueOf(today));
	}

}
