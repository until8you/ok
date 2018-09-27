package com.tedu.note.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class PointCutAspect {

//	@Before("bean(*Service)")
//	public void test1() {
//		System.out.println("切入点测试！");
//	}
	
//	@Before("within(com.tedu.*.service.*erviceImpl)")
//	public void test2() {
//		System.out.println("类切入点");
//	}
	
	/**
	 * * 代表任意修饰词
	 * 。。 代表任意参数
	 */
//	@Before("execution(* com.tedu.note.service.UserService.login(..))")
//	public void test3() {
//		System.out.println("方法切入点");
//	}
	@Before("execution(* com.tedu.note.*.*Service.list*(..))")
	public void test3() {
		System.out.println("方法切入点 一致有规则的方法命名");
	}
}
