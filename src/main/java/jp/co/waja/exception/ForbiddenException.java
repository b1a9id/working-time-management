package jp.co.waja.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends ServiceException {

	public ForbiddenException() {
		super();
	}

	public ForbiddenException(Throwable cause) {
		super(cause);
	}

	public ForbiddenException(String message) {
		super(message);
	}

	public ForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}
}
