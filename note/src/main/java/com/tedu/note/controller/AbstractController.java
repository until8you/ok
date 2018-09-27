package com.tedu.note.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.note.util.JsonResult;

public abstract class AbstractController {
	
	//处理一般异常，这里先处理级别低的异常，再处理这里的异常
	@ExceptionHandler(Exception.class)//表示处理所有异常
	@ResponseBody
	public JsonResult handleException(Throwable e) {
		e.printStackTrace();
		return new JsonResult(e);
	}
}