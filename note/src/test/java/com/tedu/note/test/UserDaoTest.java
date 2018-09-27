package com.tedu.note.test;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;

import com.tedu.note.dao.UserDao;
import com.tedu.note.entity.Note;
import com.tedu.note.entity.User;

public class UserDaoTest extends BaseTest {
	UserDao dao = null;
	@Before
	public void initDao() {
		dao = cpxac.getBean("userDao",UserDao.class);
	}
	@Test
	public void testFindUserByName() {
		User user = dao.findUserByName("demo");
		System.out.println(user);
	}
	
	@Test
	public void testAddUser() {
		User user = new User();
		//生成独一无二的字符串方法，通常用于id用
		String id = UUID.randomUUID().toString();
		user.setId(id);
		user.setName("Tom");
		String salt = "今天你吃了吗？";
		String pwd = DigestUtils.md5Hex(salt+"123456");
		user.setPassword(pwd);
		user.setNick("");
		user.setToken("");
		int n = dao.addUser(user);
		System.out.println(n);
	}
}
