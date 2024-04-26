package com.sudhirindustries.ws.emailnotificationmicroservice.error;

public class NotRetryableException extends RuntimeException{

	public NotRetryableException() {
		super();
	}

	public NotRetryableException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotRetryableException(String message) {
		super(message);
	}

	public NotRetryableException(Throwable cause) {
		super(cause);
	}
}
