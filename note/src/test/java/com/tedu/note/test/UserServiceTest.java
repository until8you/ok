package com.tedu.note.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tedu.note.entity.User;
import com.tedu.note.service.UserService;

public class UserServiceTest extends BaseTest{
	UserService service;
	@Before
	public void initService() {
		service = cpxac.getBean("userService",UserService.class);
	}
	@Test
	public void testLogin() {
		User user = service.login("demo", "123456");
		System.out.println(user);
	}
	
	@Test
	public void testRegist() {
		User user = service.regist("Andy", "Andy", "123456", "123456");
		System.out.println(user);
	}
	
}
