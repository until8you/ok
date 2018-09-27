package com.tedu.note.test;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.tedu.note.dao.NoteDao;
import com.tedu.note.entity.Note;
import com.tedu.note.test.BaseTest;

public class NoteDaoTest extends BaseTest{
	
	private NoteDao noteDao;
	@Before
	public void initNoteDao() {
		noteDao = cpxac.getBean("noteDao", NoteDao.class);
	}
	
	@Test
	public void testNoteDao() {
		String notebookId="6d763ac9-dca3-42d7-a2a7-a08053095c08";
		List<Map<String, Object>> listnote = noteDao.findNotesByNotebookId(notebookId);
		for (Map<String, Object> map : listnote) {
		System.out.println(map);	
		}
		
	}
	
	@Test
	public void testFindNoteById() {
		String noteId = "003ec2a1-f975-4322-8e4d-dfd206d6ac0c";
		Note note = noteDao.findNoteById(noteId);
		System.out.println(note);
		
	}
	
	@Test
	public void testAddNote() {
		String noteId = "046b0110-67f9-48c3-bef3-b0b23bda9d4e";
		Note note = noteDao.findNoteById(noteId);
		note.setNoteId("000000");
		note.setTitle("恐龙日记");
		int n = noteDao.addNote(note);
		System.out.println(n);
	}
	
	@Test
	public void testUpdateNote() {
		Note note = new Note();
		String noteId = "019cd9e1-b629-4d8d-afd7-2aa9e2d6afe0";
		note.setNoteId(noteId);
		note.setTitle(null);
		note.setBody("testvhjjh");
		note.setLastModifyTime(System.currentTimeMillis());
		noteDao.updateNote(note);
		note = noteDao.findNoteById(noteId);
		System.out.println(note);
	}
	
	@Test
	public void testUpdate() {
		String noteId = "019cd9e1-b629-4d8d-afd7-2aa9e2d6afe0";
		Note note = noteDao.findNoteById(noteId);
		System.out.println(note);
		note.setNotebookId("123");
		System.out.println(note);
		int i = noteDao.updateNote(note);
		System.out.println(i);
	}
	
	@Test
	public void testFindNotesByStatusId() {
		List<Map<String,Object>> notes = noteDao.findNotesByStatusId();
		for (Map<String, Object> map : notes) {
			System.out.println(map);
		}
		
	}
	
	//批量删除测试
	@Test
	public void testDeleteNotes() {
		String id1 = "019cd9e1-b629-4d8d-afd7-2aa9e2d6afe0";
		String id2 = "01da5d69-89d5-4140-9585-b559a97f9cb0";
		
		int n = noteDao.deleteNotes(id1,id2);
		System.out.println(n);
	}
}
