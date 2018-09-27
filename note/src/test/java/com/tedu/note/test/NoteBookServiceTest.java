package com.tedu.note.test;


import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.tedu.note.entity.NoteBook;
import com.tedu.note.service.NoteBookService;

public class NoteBookServiceTest extends BaseTest{

	NoteBookService service;
	@Before
	public void initService() {
		service = cpxac.getBean("noteBookService", NoteBookService.class);
	}
	
	@Test
	public void testListNoteBook() {
		String userId = "48595f52-b22c-4485-9244-f4004255b972";
		List<Map<String,Object>> list = service.listNoteBooks(userId);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
	@Test
	public void testAdd() {
		String name = "µÁÄ¹±Ê¼Ç2";
		String userId = "39295a3d-cc9b-42b4-b206-a2e7fab7e77c";
		NoteBook book = service.add(name, userId);
		System.out.println(book);
	}
	
	@Test
	public void testFindNotebooksByPage() {
		String userId = "48595f52-b22c-4485-9244-f4004255b972";
		int page = 0;
		
		List<Map<String,Object>> list = service.listNoteBooks(userId, page);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
}
