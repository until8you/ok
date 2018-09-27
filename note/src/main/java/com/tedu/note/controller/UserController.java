package com.tedu.note.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tedu.note.entity.User;
import com.tedu.note.service.PassWordException;
import com.tedu.note.service.UserNameException;
import com.tedu.note.service.UserNotFondException;
import com.tedu.note.service.UserService;
import com.tedu.note.util.JsonResult;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController{

	@Resource
	private UserService userService;
	
	//登陆功能
	@RequestMapping("/login.do")
	@ResponseBody
	public Object login(String name, String password,HttpSession session) {
			User user = userService.login(name, password);
			//登陆成功，将登陆状态添加到session中，用于拦截器的校验登陆
			session.setAttribute("loginUser", user);
			return new JsonResult(user);
	}
	
	//注册功能
	@RequestMapping("/regist.do")
	@ResponseBody
	public Object regist(String name,String nick,String password,String confirm) {
		User user = userService.regist(name, nick, password, confirm);
		return new JsonResult(user);
	}
	
	//注册功能
	@RequestMapping("/hart.do")
	@ResponseBody
	public Object hart() {
		return new JsonResult("ok");
	}
	
	/*
	 * @ResponseBody 会自动处理控制结果
	 * 1.如果是Javabean(集合，数组)，返回json
	 * 2.如果是byte数组，则将byte数组直接装入响应消息的body中
	 * 3.produces 设置响应contenttype
	 */
	@RequestMapping(value="/image.do",produces="image/png")
	@ResponseBody
	public byte[] image() throws Exception {
		return createPng();
	}
	
	//下载图片
	@RequestMapping(value="/downloading.do",produces=" application/octet-stream")
	@ResponseBody
	public byte[] downLoad(HttpServletResponse res) throws Exception {
		res.setHeader("Content-Disposition", "attachment; filename=\"demo.png\"");
		return createPng();
	}
	
	//下载Excel
	@RequestMapping(value="/excel.do",produces=" application/octet-stream")
	@ResponseBody
	public byte[] excel(HttpServletResponse res) throws Exception {
		res.setHeader("Content-Disposition", "attachment; filename=\"demo.xls\"");
		return createExcel();
	}
	
	//把异常类型的状态处理到status
	@ExceptionHandler(UserNotFondException.class)//表示处理所有异常
	@ResponseBody
	public Object handlerUserNotFond(UserNotFondException e) {
		e.printStackTrace();
		return new JsonResult(2,e);
	}
	
	@ExceptionHandler(UserNameException.class)
	@ResponseBody
	public Object handleUserName(UserNameException e) {
		e.printStackTrace();
		//status==4代表登陆用户名为空
		return new JsonResult(4,e);
	}
	
	@ExceptionHandler(PassWordException.class)//表示处理所有异常
	@ResponseBody
	public Object handlerPassWord(PassWordException e) {
		e.printStackTrace();
		return new JsonResult(3,e);
	}
	
	
	/*
	 * 创建一个图片，编码为PNG，返回编码以后的数据
	 * 
	 */
	private byte[] createPng() throws IOException {
		BufferedImage img = new BufferedImage(200, 80, BufferedImage.TYPE_3BYTE_BGR);
		//图片绘制
		img.setRGB(100, 40, 0xffffff);
		//图片编码成png ByteArrayOutputStream内存数组 比文件快上万倍
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(img, "png", out);
		//关闭流
		out.close();
		//转换成bytes
		byte[] png = out.toByteArray();
		return png;
	}
	
	//生成Excel 
	private byte[] createExcel() throws IOException{
		//需要导入生成excel的包org.apache.poi 3.13
		//生成工作布
		HSSFWorkbook workbook = new HSSFWorkbook();
		//生成工作表
		HSSFSheet sheet = workbook.createSheet();
		//生成行
		HSSFRow row = sheet.createRow(0);
		//生成cell
		HSSFCell cell = row.createCell(0);
		//填入内容
		cell.setCellValue("Hello Word!");
		
		//生成内存数组流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		//将工作布放入流中
		workbook.write(out);
		out.close();
		
		return out.toByteArray();
		
	}
	
	
	//这里上传是通过springmvc的 需要配置解析器 CommonsmultipatResolver
	//文档上传
	@RequestMapping("/upload.do")
	@ResponseBody
	public JsonResult upload(MultipartFile userfile1,MultipartFile userfile2) throws Exception {
		//获取源文件名
		String name1 = userfile1.getOriginalFilename();
		String name2 = userfile2.getOriginalFilename();
		
		System.out.println(name1);
		System.out.println(name2);
		
		//1.第一种保存
		File dir = new File("D:/demo");
		dir.mkdir();
		File f1 = new File(dir,name1);
		File f2 = new File(dir,name2);
//		userfile1.transferTo(f1);
		
		//3.利用流复制数据
		InputStream in1 = userfile1.getInputStream();
		FileOutputStream out1 = new FileOutputStream(f1);
		int b;
		while((b=in1.read())!=-1) {
			out1.write(b);
		}
		in1.close();
		out1.close();
		InputStream in2 = userfile2.getInputStream();
		FileOutputStream out2= new FileOutputStream(f2);
		byte[] buf = new byte[8*1024];
		int n;
		while((n=in2.read(buf))!=-1) {
			out2.write(buf,0,n);
		}
		in2.close();
		out2.close();
		
		return new JsonResult(true);
		
	}
}
