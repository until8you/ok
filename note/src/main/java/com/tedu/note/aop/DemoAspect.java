package com.tedu.note.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.tedu.note.service.UserNotFondException;
import com.tedu.note.util.JsonResult;

/**
 * 
 * 创建一个切面组件，就是一个普通的Javabean
 *
 */
//@Component
//@Aspect
public class DemoAspect {
	//这里时aspectj包下的 before叫通知 是指切面方法的执行时机在目标方法之前执行
	//声明test方法，将在userService中所有方法之前执行
	@Before("bean(userService)")
	public void test() {
		System.out.println("AOP : hello aspectj");
	}
	
	//执行时机是在目标方法之后执行
	@After("bean(userService)")
	public void test2() {
		System.out.println("After");
	}
	
	@AfterReturning("bean(userService)")
	public void test3() {
		System.out.println("afterReturning");
	}
	
	@AfterThrowing("bean(userService)")
	public void test4() {
		System.out.println("AfterThrowing");
	}
	
	/**
	 * 环绕通知
	 * 1.必须有返回值
	 * 2.必须有参数
	 * 3.必须抛异常
	 * 4.
	 */
	//@Around("bean(userService)")
	public Object test5(ProceedingJoinPoint pj) throws Throwable{
		Object val = pj.proceed();
		System.out.println("业务结果："+val);
		return new UserNotFondException("就是不让登陆");
	}
}
