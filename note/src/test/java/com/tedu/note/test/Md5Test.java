package com.tedu.note.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
/*
 * 摘要的生成 需要导入 commons-codec依赖包 可以把字符串和文件传换成密文摘要
 */
public class Md5Test {

	@Test
	public void testMd5() {
		String str = "123456";
		String md5 = DigestUtils.md5Hex(str);
		System.out.println(md5);
		//加盐摘要 安全性比较高
		String salt = "今天你吃了码？";
		md5 = DigestUtils.md5Hex(salt+str);
		System.out.println(md5);
	}
}
