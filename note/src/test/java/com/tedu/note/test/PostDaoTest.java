package com.tedu.note.test;

import org.junit.Before;
import org.junit.Test;

import com.tedu.note.dao.PostDao;
import com.tedu.note.entity.Post;

public class PostDaoTest extends BaseTest{

	PostDao dao;
	@Before
	public void init() {
		dao = cpxac.getBean("postDao",PostDao.class);
	}
	
	@Test
	public void testFindPostById() {
		Post post = dao.findPostById(1);
		System.out.println(post);
	}
}
