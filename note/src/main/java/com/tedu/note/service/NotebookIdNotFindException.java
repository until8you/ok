package com.tedu.note.service;

public class NotebookIdNotFindException extends RuntimeException {

	
	private static final long serialVersionUID = 6733230337657557095L;

	public NotebookIdNotFindException() {
	}

	public NotebookIdNotFindException(String arg0) {
		super(arg0);
	}

	public NotebookIdNotFindException(Throwable arg0) {
		super(arg0);
	}

	public NotebookIdNotFindException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NotebookIdNotFindException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
