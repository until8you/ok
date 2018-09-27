package com.tedu.note.service;

import java.util.List;
import java.util.Map;

import com.tedu.note.entity.Note;

public interface NoteService {

	List<Map<String,Object>> listNotes(String notebookId) throws NotebookIdNotFindException;
	
	Note findNote(String noteId) throws NoteIdNotFoundException;
	
	Note addNote(String title,String notebookId,String userId) throws NoteIdNotFoundException,NotebookIdNotFindException,UserIdNotFoundException;

	boolean update(String noteId,String title,String body)throws NoteIdNotFoundException;
	
	boolean moveNote(String noteId,String notebookId)throws NoteIdNotFoundException,NotebookIdNotFindException;
	
	boolean deleteNote(String noteId) throws NoteIdNotFoundException;
	
	List<Map<String,Object>> listTrashNotes(String userId)throws UserNotFondException;
	
	boolean replayNote(String noteId,String notebookId)throws NoteIdNotFoundException,NotebookIdNotFindException;
	
	boolean rollbackNote(String noteId,String userId)throws UserNotFondException,NoteIdNotFoundException;

	int deleteNotes(String... noteIds)throws NoteIdNotFoundException;
	
	boolean addStars(String userId,int stars) throws UserNotFondException;
}
