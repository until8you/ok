package com.tedu.note.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import com.tedu.note.dao.NoteBookDao;
import com.tedu.note.dao.NoteDao;
import com.tedu.note.dao.StarsDao;
import com.tedu.note.dao.UserDao;
import com.tedu.note.entity.Note;
import com.tedu.note.entity.Stars;
import com.tedu.note.entity.User;

@Service("noteService")
//@Transactional //类上添加，类中的方法都会被事务管理
public class NoteServiceImpl implements NoteService{

	@Resource
	private NoteDao noteDao;
	@Resource
	private NoteBookDao noteBookDao;
	@Resource
	private UserDao userDao;
	public List<Map<String, Object>> listNotes(String notebookId) throws NotebookIdNotFindException{
		//对传入的参数进行判断
		if(notebookId==null||notebookId.trim().isEmpty()) {
			throw new NotebookIdNotFindException("notebookId为空");
		}
		//根据id能否找到笔记本 这种方式更省流量
		int n = noteBookDao.countNotebookById(notebookId);
		if(n!=1) {
			throw new NotebookIdNotFindException("没有id对应的笔记本");
		}
		
		//List<Map<String, Object>> Notes = noteDao.findNotesByNotebookId(notebookId);
		List<Map<String, Object>> Notes = noteDao.findNotes(null, notebookId, "1");
		return Notes;
	}
	
	public Note findNote(String noteId) throws NoteIdNotFoundException {
		if(noteId==null||noteId.trim().isEmpty()) {
			throw new NoteIdNotFoundException("noteId为空");
		}
		Note note = noteDao.findNoteById(noteId);
		if(note==null) {
			throw new NoteIdNotFoundException("noteId没有对应的笔记");
		}
		return note;
	}
	//添加笔记业务
	//@Transactional
	public Note addNote(String title, String notebookId, String userId) throws TitleIsNullException,NotebookIdNotFindException,UserIdNotFoundException{
		//判断传来的参数
		if(notebookId==null||notebookId.trim().isEmpty()) {
			throw new NotebookIdNotFindException("notebookId为空");
		}
		if(userId==null||userId.trim().isEmpty()) {
			throw new UserIdNotFoundException("userId为空");
		}
		int n = noteBookDao.countNotebookById(notebookId);
		if(n!=1) {
			throw new NotebookIdNotFindException("notebookId没有对应的笔记本");
		}
		User user = userDao.findUserById(userId);
		if(user==null) {
			throw new UserNotFondException("不存在用户");
		}
		
		String noteId = UUID.randomUUID().toString();
		String statusId = "1";
		String typeId = "1";
		String body = "";
		long createTime = System.currentTimeMillis();
		long lastModifyTime = System.currentTimeMillis();
		Note note = new Note(noteId,notebookId,userId,statusId,typeId,title,body,createTime,lastModifyTime);
		int i = noteDao.addNote(note);
		if(i!=1) {
			throw new TitleIsNullException("保存失败");
		}
		//当前事务会传播到addStars方法中，整合成一个事务
		addStars(userId,-11);
		return note;
	}

	public boolean update(String noteId, String title, String body) throws NoteIdNotFoundException{
		if(noteId==null||noteId.trim().isEmpty()) {
			throw new NoteIdNotFoundException("noteId不能为空");
		}
		Note note = noteDao.findNoteById(noteId);
		if(note==null) {
			throw new NoteIdNotFoundException("没有对应的笔记对象");
		}
		Note data = new Note();
		if(title!=null&&!title.equals(note.getTitle())) {
			data.setTitle(title);
		}
		if(body!=null&&!body.equals(note.getBody())) {
			data.setBody(body);
		}
		data.setNoteId(noteId);
		System.out.println(data);
		data.setLastModifyTime(System.currentTimeMillis());
		System.out.println(data);
		int n = noteDao.updateNote(data);
		if(n!=1) {
			throw new NoteIdNotFoundException("更新失败");
		}
		return n==1;
	}
	
	public boolean moveNote(String noteId, String notebookId) throws NoteIdNotFoundException,NotebookIdNotFindException {
		if(notebookId==null||notebookId.trim().isEmpty()) {
			throw new NotebookIdNotFindException("notebookId为空");
		}
		if(noteId==null||noteId.trim().isEmpty()) {
			throw new NoteIdNotFoundException("noteId不能为空");
		}
		Note note = noteDao.findNoteById(noteId);
		if(note==null) {
			throw new NoteIdNotFoundException("没有对应的笔记对象");
		}
		note.setNoteId(noteId);
		note.setNotebookId(notebookId);
		note.setLastModifyTime(System.currentTimeMillis());
		int n = noteDao.updateNote(note);
		return n==1;
	}

	public boolean deleteNote(String noteId) throws NoteIdNotFoundException {
		if(noteId==null||noteId.trim().isEmpty()) {
			throw new NoteIdNotFoundException("noteId为空");
		}
		Note note = noteDao.findNoteById(noteId);
		if(note==null) {
			throw new NoteIdNotFoundException("noteId没有对应的笔记");
		}
		note.setStatusId("0");
		note.setLastModifyTime(System.currentTimeMillis());
		int n = noteDao.updateNote(note);
		return n==1;
	}
	
	public List<Map<String,Object>> listTrashNotes(String userId)throws UserNotFondException{
		if(userId==null||userId.trim().isEmpty()) {
			throw new UserNotFondException("userId为空");
		}
		User user = userDao.findUserById(userId);
		if(user==null) {
			throw new UserNotFondException("没有对应的笔记");
		}
		//List<Map<String,Object>> notes = noteDao.findNotesByStatusId();
		return noteDao.findNotes(userId, null, "0");
	}

	public boolean replayNote(String noteId, String notebookId)
			throws NoteIdNotFoundException, NotebookIdNotFindException {
		if(notebookId==null||notebookId.trim().isEmpty()) {
			throw new NotebookIdNotFindException("notebookId为空");
		}
		if(noteId==null||noteId.trim().isEmpty()) {
			throw new NoteIdNotFoundException("noteId不能为空");
		}
		Note note = noteDao.findNoteById(noteId);
		if(note==null) {
			throw new NoteIdNotFoundException("没有对应的笔记对象");
		}
		
		note.setNoteId(noteId);
		note.setNotebookId(notebookId);
		note.setStatusId("1");
		note.setLastModifyTime(System.currentTimeMillis());
		
		int n = noteDao.updateNote(note);
		return n==1;
	}

	public boolean rollbackNote(String noteId, String userId) throws UserNotFondException, NoteIdNotFoundException {
		if(userId==null||userId.trim().isEmpty()) {
			throw new UserNotFondException("userId为空");
		}
		User user = userDao.findUserById(userId);
		if(user==null) {
			throw new UserNotFondException("没有对应的笔记");
		}
		if(noteId==null||noteId.trim().isEmpty()) {
			throw new NoteIdNotFoundException("noteId不能为空");
		}
		Note note = noteDao.findNoteById(noteId);
		if(note==null) {
			throw new NoteIdNotFoundException("没有对应的笔记对象");
		}
		
		int n = noteDao.deleteNote(noteId);
		
		return n==1;
	}

	//用于测试批量删除的事务 String...相当于String[]  String...调用时比较省事  ...变长参数
	//@Transactional
	public int deleteNotes(String... noteIds) throws NoteIdNotFoundException {
//		for (String id : noteIds) {
//			int n = noteDao.deleteNote(id);
//			if(n!=1) {
//				throw new NoteIdNotFoundException("id错误");
//			}
//			
//		}
		//一条sql的性能明显会比一条一条删除效果明显
		int n = noteDao.deleteNotes(noteIds);
		if(n!=noteIds.length) {
			throw new NoteIdNotFoundException("id错误");
		}
		return n;
	}

	
	@Resource
	private StarsDao starsDao;
	
	//@Transactional
	public boolean addStars(String userId, int stars) throws UserNotFondException {
		if(userId==null||userId.trim().isEmpty()) {
			throw new UserNotFondException("userId为空");
		}
		User user = userDao.findUserById(userId);
		if(user==null) {
			throw new UserNotFondException("没有对应的笔记");
		}
		
		//检查是否有星
		Stars st = starsDao.findStarsByUserId(userId);
		System.out.println(st);
		if(st==null) {
			//如果没有星星
			String id = UUID.randomUUID().toString();
			st = new Stars(id,userId,stars);
			int n = starsDao.insertStars(st);
			if(n!=1) {
				throw new RuntimeException("插入失败");
			}
		}else {//如果有星星
			int n = st.getStars()+stars;
			//stars为负数时，可能会发生扣分到负数的情况
			if(n<0) {
				throw new RuntimeException("扣分太多");
			}
			st.setStars(st.getStars()+stars);
			n = starsDao.updateStars(st);
			if(n!=1) {
				throw new RuntimeException("插入失败");
			}
		}
		
		return true;
	}
}
