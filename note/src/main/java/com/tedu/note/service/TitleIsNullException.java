package com.tedu.note.service;

public class TitleIsNullException extends RuntimeException {

	private static final long serialVersionUID = 6501842500733556560L;

	public TitleIsNullException() {
	}

	public TitleIsNullException(String arg0) {
		super(arg0);
	}

	public TitleIsNullException(Throwable arg0) {
		super(arg0);
	}

	public TitleIsNullException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public TitleIsNullException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
