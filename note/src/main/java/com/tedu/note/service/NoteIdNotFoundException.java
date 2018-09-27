package com.tedu.note.service;

public class NoteIdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4008638891701237796L;

	public NoteIdNotFoundException() {
	}

	public NoteIdNotFoundException(String arg0) {
		super(arg0);
	}

	public NoteIdNotFoundException(Throwable arg0) {
		super(arg0);
	}

	public NoteIdNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NoteIdNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
