package jp.co.waja.exception;

public class DuplicatedException extends ServiceException {
	public DuplicatedException() {
		super();
	}

	public DuplicatedException(Throwable cause) {
		super(cause);
	}

	public DuplicatedException(String message) {
		super(message);
	}

	public DuplicatedException(String message, Throwable cause) {
		super(message, cause);
	}
}
