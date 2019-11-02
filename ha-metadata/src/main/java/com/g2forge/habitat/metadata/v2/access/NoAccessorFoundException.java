package com.g2forge.habitat.metadata.v2.access;

public class NoAccessorFoundException extends IllegalArgumentException {
	private static final long serialVersionUID = -487784903858888733L;

	public NoAccessorFoundException() {}

	public NoAccessorFoundException(String message) {
		super(message);
	}

	public NoAccessorFoundException(Throwable cause) {
		super(cause);
	}

	public NoAccessorFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
