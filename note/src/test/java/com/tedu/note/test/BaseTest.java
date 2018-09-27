package com.tedu.note.test;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/*
 * 一般把公共的方法和属性，提取出抽象类，不需要实例化此类
 */
public abstract class BaseTest {

	protected ClassPathXmlApplicationContext cpxac = null;


	@Before
	public void testUserDao() {
		cpxac = new ClassPathXmlApplicationContext( "conf/spring-mvc.xml","conf/spring-service.xml","conf/spring-mybatis.xml");
	}

	@After
	public void closeAC() {
		cpxac.close();
	}

}