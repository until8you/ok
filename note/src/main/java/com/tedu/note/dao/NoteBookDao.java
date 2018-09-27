package com.tedu.note.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tedu.note.entity.NoteBook;

public interface NoteBookDao {

	List<Map<String,Object>> findNoteBooksByUesrId(String userId);
	
	int countNotebookById(String notebookId);
	
	int addNoteBook(NoteBook noteBook);
	
	List<Map<String,Object>> findNoteBooksByPage(
			@Param("userId") String userId,
			@Param("start") int start,
			@Param("pageSize") int pageSize,
			@Param("table") String table
			);
}
