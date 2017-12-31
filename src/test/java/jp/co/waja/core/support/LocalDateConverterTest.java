package jp.co.waja.core.support;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

class LocalDateConverterTest {

	@Nested
	class convertToDatabaseColumn {
		LocalDateConverter converter;

		LocalDate target;

		@BeforeEach
		void beforeEach() {
			converter = new LocalDateConverter();
		}

		@Test
		void argNull() {
			Date result = converter.convertToDatabaseColumn(target);
			Assertions.assertThat(result).isNull();
		}

		@Test
		void argsNotNull() {
			target = LocalDate.of(2017, 12, 31);

			Date result = converter.convertToDatabaseColumn(target);
			Assertions.assertThat(result).isEqualTo(Date.valueOf(target));
		}
	}

}
