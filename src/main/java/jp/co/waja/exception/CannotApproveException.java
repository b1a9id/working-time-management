package jp.co.waja.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class CannotApproveException extends ServiceException {

	public CannotApproveException() {
		super();
	}

	public CannotApproveException(Throwable cause) {
		super(cause);
	}

	public CannotApproveException(String message) {
		super(message);
	}

	public CannotApproveException(String message, Throwable cause) {
		super(message, cause);
	}
}
