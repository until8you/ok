package com.tedu.note.service;

public class PassWordException extends RuntimeException {

	private static final long serialVersionUID = -8757868311344650104L;

	public PassWordException() {
	}

	public PassWordException(String arg0) {
		super(arg0);
	}

	public PassWordException(Throwable arg0) {
		super(arg0);
	}

	public PassWordException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PassWordException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
