package com.tedu.note.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.note.entity.NoteBook;
import com.tedu.note.service.NoteBookService;
import com.tedu.note.service.UserNotFondException;
import com.tedu.note.util.JsonResult;

@Controller
@RequestMapping("/notebook")
public class NoteBookController extends AbstractController {
	
	@Resource
	private NoteBookService noteBookService;
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(String userId) {
		List<Map<String, Object>> list = noteBookService.listNoteBooks(userId);
		return new JsonResult(list);
	}
	
	@RequestMapping("/add.do")
	@ResponseBody
	public JsonResult add(String name,String userId) {
		NoteBook noteBook = noteBookService.add(name, userId);
		return new JsonResult(noteBook);
		
	}
	
	@RequestMapping("/page.do")
	@ResponseBody
	public JsonResult page(String userId,Integer page) {
		List<Map<String, Object>> list = noteBookService.listNoteBooks(userId,page);
		return new JsonResult(list);
	}
}
