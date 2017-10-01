package jp.co.waja.app.support;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class PageWrapper<T> {
	private Page<T> page;

	private int previousNumber;

	private int currentNumber;

	private int nextNumber;

	public PageWrapper(Page<T> page) {
		this.page = page;
		this.currentNumber = page.getNumber() + 1;
		this.previousNumber = isFirst() ? 0 : getCurrentNumber() - 1;
		this.nextNumber = isLast() ? 0 : getCurrentNumber() + 1;
	}

	public boolean isFirst() {
		return page.isFirst();
	}

	public boolean isLast() {
		return page.isLast();
	}

	public int getNumber() {
		return page.getNumber();
	}

	public int getLastNumber() {
		return page.getTotalPages() - 1;
	}

	public int getTotalPages() {
		return page.getTotalPages();
	}

	public boolean hasPrevious() {
		return page.hasPrevious();
	}

	public boolean hasNext() {
		return page.hasNext();
	}
}
