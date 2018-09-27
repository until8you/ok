package com.tedu.note.service;

public class UserNotFondException extends RuntimeException {

	private static final long serialVersionUID = -5420854196256910977L;

	public UserNotFondException() {
	}

	public UserNotFondException(String arg0) {
		super(arg0);
	}

	public UserNotFondException(Throwable arg0) {
		super(arg0);
	}

	public UserNotFondException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public UserNotFondException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
