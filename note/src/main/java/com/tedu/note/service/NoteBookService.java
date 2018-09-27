package com.tedu.note.service;

import java.util.List;
import java.util.Map;

import com.tedu.note.entity.NoteBook;

public interface NoteBookService {

	List<Map<String,Object>>listNoteBooks(String userId) throws UserNotFondException;
	
	NoteBook add(String name,String userId) throws UserNotFondException,NoteBookNameNullException;
	
	List<Map<String,Object>>listNoteBooks(String userId,Integer page) throws UserNotFondException;
}
