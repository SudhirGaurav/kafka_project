package com.sudhirindustries.ws.emailnotificationmicroservice.error;

public class RetryableException extends RuntimeException {

	public RetryableException() {
		super();
	}

	public RetryableException(String msg) {
		super(msg);
	}
	
	public RetryableException(Throwable cause) {
		super(cause);
	}
	public RetryableException(String message, Throwable cause) {
		super(message, cause);
	}

	
}
