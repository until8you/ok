package com.tedu.note.test;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.tedu.note.dao.NoteBookDao;
import com.tedu.note.entity.NoteBook;

public class NoteBookDaoTest extends BaseTest{

	NoteBookDao dao;
	
	@Before
	public void initDao() {
		dao = cpxac.getBean("noteBookDao", NoteBookDao.class);
	}
	
	@Test
	public void testFindNoteBooksByUserId() {
		String id = "39295a3d-cc9b-42b4-b206-a2e7fab7e77c";
		List<Map<String,Object>> list = dao.findNoteBooksByUesrId(id);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
	@Test
	public void testCountNoteBooksById() {
		String id = "6d763ac9-dca3-42d7-a2a7-a08053095c08";
		int n = dao.countNotebookById(id);
		System.out.println(n);
	}
	//测试添加一组笔记本
	@Test
	public void testAddNoteBook() {
		String noteBookId = UUID.randomUUID().toString();
		String userId = "39295a3d-cc9b-42b4-b206-a2e7fab7e77c";
		String typeId = "5";
		String name = "盗墓笔记";
		String desc = "";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = sdf.format(date);
		System.out.println(createTime);
		NoteBook noteBook = new NoteBook(noteBookId, userId, typeId, name, desc, createTime);
		int n = dao.addNoteBook(noteBook);
		System.out.println(n);
	}
	
	
}
