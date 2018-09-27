package com.tedu.note.test;

//√Ê ‘Ã‚
public class Demo1 {

	public static void main(String[] args) {
		int i = 2;
		final int[] ary = {2};
		test(i,ary);
		System.out.println(i);//2
		System.out.println(ary[0]);//3
	}
	
	public static void test(Integer i,int[] ary) {
		i=i++;
		ary[0]++;
	}
}
