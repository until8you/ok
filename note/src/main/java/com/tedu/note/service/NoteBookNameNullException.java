package com.tedu.note.service;

public class NoteBookNameNullException extends RuntimeException {

	private static final long serialVersionUID = -8221049065751010295L;

	public NoteBookNameNullException() {
	}

	public NoteBookNameNullException(String arg0) {
		super(arg0);
	}

	public NoteBookNameNullException(Throwable arg0) {
		super(arg0);
	}

	public NoteBookNameNullException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NoteBookNameNullException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
