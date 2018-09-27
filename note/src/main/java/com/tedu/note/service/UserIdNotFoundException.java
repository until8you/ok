package com.tedu.note.service;

public class UserIdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6958537774051117049L;

	public UserIdNotFoundException() {
	}

	public UserIdNotFoundException(String arg0) {
		super(arg0);
	}

	public UserIdNotFoundException(Throwable arg0) {
		super(arg0);
	}

	public UserIdNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public UserIdNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
