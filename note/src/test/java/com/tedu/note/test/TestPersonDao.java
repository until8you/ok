package com.tedu.note.test;

import org.junit.Before;
import org.junit.Test;

import com.tedu.note.dao.NoteBookDao;
import com.tedu.note.dao.PersonDao;
import com.tedu.note.entity.Person;

public class TestPersonDao extends BaseTest{
	PersonDao dao;
	
	@Before
	public void initDao() {
		dao = cpxac.getBean("personDao", PersonDao.class);
	}

	@Test
	public void testAddPerson() {
		Person p = new Person(null,"Tom");
		System.out.println(p);
		int n = dao.addPerson(p);
		System.out.println(n);
		System.out.println(p);
	}
}
