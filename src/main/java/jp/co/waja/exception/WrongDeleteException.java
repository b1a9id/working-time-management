package jp.co.waja.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongDeleteException extends ServiceException {

	public WrongDeleteException() {
		super();
	}

	public WrongDeleteException(Throwable cause) {
		super(cause);
	}

	public WrongDeleteException(String message) {
		super(message);
	}

	public WrongDeleteException(String message, Throwable cause) {
		super(message, cause);
	}
}
