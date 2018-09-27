package com.tedu.note.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.note.entity.Note;
import com.tedu.note.service.NoteService;
import com.tedu.note.util.JsonResult;

@Controller
@RequestMapping("/note")
public class NoteController extends AbstractController {

	@Resource
	private NoteService noteService;
	
	@RequestMapping("/findnote.do")
	@ResponseBody
	public JsonResult listNotes(String notebookId) {
		List<Map<String, Object>> notes = noteService.listNotes(notebookId);
		return new JsonResult(notes);
	}
	
	@RequestMapping("/body.do")
	@ResponseBody
	public JsonResult findNote(String noteId) {
		Note note = noteService.findNote(noteId);
		return new JsonResult(note);
	}
	
	@RequestMapping("/add.do")
	@ResponseBody
	public JsonResult addNote(String title,String notebookId,String userId) {
		Note note = noteService.addNote(title, notebookId, userId);
		return new JsonResult(note);
	}
	
	@RequestMapping("/update.do")
	@ResponseBody
	public JsonResult update(String noteId,String title,String body) {
		boolean success = noteService.update(noteId, title, body);
		return new JsonResult(success);
	}
	
	@RequestMapping("/move.do")
	@ResponseBody
	public JsonResult update(String noteId,String notebookId) {
		boolean success = noteService.moveNote(noteId,notebookId);
		return new JsonResult(success);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(String noteId) {
		boolean success = noteService.deleteNote(noteId);
		return new JsonResult(success);
	}
	
	@RequestMapping("/listtrash.do")
	@ResponseBody
	public JsonResult listTrashNotes(String userId) {
		List<Map<String, Object>> notes = noteService.listTrashNotes(userId);
		return new JsonResult(notes);
	}
	
	@RequestMapping("/replay.do")
	@ResponseBody
	public JsonResult replayNote(String noteId,String notebookId) {
		boolean b = noteService.replayNote(noteId,notebookId);
		return new JsonResult(b);
	}
	
	@RequestMapping("/rollback.do")
	@ResponseBody
	public JsonResult rollbackNote(String noteId,String userId) {
		boolean b = noteService.rollbackNote(noteId,userId);
		return new JsonResult(b);
	}
}
