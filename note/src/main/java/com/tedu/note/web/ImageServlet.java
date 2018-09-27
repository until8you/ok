package com.tedu.note.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImageServlet
 */
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//发送图片
		byte[] png = createPng();
		//设置响应参数
		response.setContentType("image/png");
		response.setContentLength(png.length);
		//把数据发送到body中 getOutputStream()发送字节数据
		response.getOutputStream().write(png);
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
	
}
