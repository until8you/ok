package com.tedu.note.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tedu.note.dao.NoteBookDao;
import com.tedu.note.dao.UserDao;
import com.tedu.note.entity.NoteBook;
import com.tedu.note.entity.User;

@Service("noteBookService")
@Transactional //类上添加，类中的方法都会被事务管理
public class NoteBookServiceImpl implements NoteBookService{

	@Resource
	private NoteBookDao noteBookDao;
	@Resource
	private UserDao userDao;
	//配置到文件中，避免写死
	@Value("#{db.pageSize}")
	private int pageSize;
	public List<Map<String, Object>> listNoteBooks(String userId) throws UserNotFondException {
		if(userId==null||userId.trim().isEmpty()) {
			throw new UserNotFondException("Id不能为空");
		}
		User user = userDao.findUserById(userId);
		if(user==null) {
			throw new UserNotFondException("用户不存在");
		}
		return noteBookDao.findNoteBooksByUesrId(userId);
	}

	//插入一个笔记本
	public NoteBook add(String name, String userId) throws UserNotFondException, NoteBookNameNullException {
		//判断数据
		if(name==null||name.trim().isEmpty()) {
			throw new NoteBookNameNullException("NoteBookNameNullException不能为空");
		}
		if(userId==null||userId.trim().isEmpty()) {
			throw new UserNotFondException("userId不能为空");
		}
		User user = userDao.findUserById(userId);
		if(user==null) {
			throw new UserNotFondException("userId没有对应的用户");
		}
		String noteBookId = UUID.randomUUID().toString();
		String typeId = "5";
		String desc = "";
		String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		NoteBook noteBook = new NoteBook(noteBookId, userId, typeId, name, desc, createTime);
		int n = noteBookDao.addNoteBook(noteBook);
		if(n!=1) {
			throw new NoteBookNameNullException("noteBook插入失败");
		}
		return noteBook;
	}

	public List<Map<String, Object>> listNoteBooks(String userId, Integer page) throws UserNotFondException {
		if(userId==null||userId.trim().isEmpty()) {
			throw new UserNotFondException("Id不能为空");
		}
		User user = userDao.findUserById(userId);
		if(user==null) {
			throw new UserNotFondException("用户不存在");
		}
		if(page==null) {
			page = 0;
		}
		int start = page*pageSize;
		String table = "cn_notebook";
		return noteBookDao.findNoteBooksByPage(userId, start, pageSize, table);
	}

	
}
