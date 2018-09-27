package com.tedu.note.test;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.tedu.note.entity.Note;
import com.tedu.note.service.NoteIdNotFoundException;
import com.tedu.note.service.NoteService;

public class NoteServiceTest extends BaseTest {
	NoteService noteService;
	@Before
	public void initNoteService() {
		noteService = cpxac.getBean("noteService", NoteService.class);
	}
	
	@Test
	public void testNoteService() {
		String notebookId="123456";
		List<Map<String, Object>> listnote = noteService.listNotes(notebookId);
		for (Map<String, Object> map : listnote) {
		System.out.println(map);	
		}
	}
	
	@Test
	public void testFindNote(){
		String noteId = "003ec2a1-f975-4322-8e4d-dfd206d6ac0c";
		Note note = noteService.findNote(noteId);
		System.out.println(note);
	}
	
	@Test
	public void testAddNote() {
		String noteId = "046b0110-67f9-48c3-bef3-b0b23bda9d4e";
		Note note = noteService.findNote(noteId);
		System.out.println(note);
		Note newNote = noteService.addNote("xxxxx", note.getNotebookId(), note.getUserId());
		System.out.println(newNote);
	}
	
	@Test
	public void testUpdate() {
		String noteId = "019cd9e1-b629-4d8d-afd7-2aa9e2d6afe0";
		String title="Test";
		String body="test1234";
		boolean b = noteService.update(noteId, title, body);
		System.out.println(b);
	}
	
	@Test
	public void testDeleteNotes() {
		String id1 = "fed920a0-573c-46c8-ae4e-368397846efd";
		String id2 = "ffc2cf21-78ed-4647-adb4-3e545613ef26";
		String id3 = "fsaf-as-df-asdf-as-df-dsa";
		String id4 = "ss19055-30e8-4cdc-bfac-97c6bad9518f";
		int n = noteService.deleteNotes(id1,id2,id3,id4);
		//Ïàµ±ÓÚint n = noteService.deleteNotes(new String{id1,id2,id3,id4});
		
	}
	
	@Test
	public void testAddStars() {
		String userId = "03590914-a934-4da9-ba4d-b41799f917d1";
		boolean b = noteService.addStars(userId, 10);
		System.out.println(b);
		b = noteService.addStars(userId, 5);
		System.out.println(b);
	}
}
