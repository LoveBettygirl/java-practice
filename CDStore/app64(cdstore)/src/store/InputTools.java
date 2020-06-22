package store;

import java.util.*;

/**
 * 输入工具类
 * @author user
 */
public class InputTools {
	
	/** 文本扫描器（用于从键盘输入数据） */
	private static Scanner sc=new Scanner(System.in);
	
	/**
	 * 判断输入的字符串是否为整数
	 * @param s
	 * @return
	 */
	private static boolean isInteger(String s) {
		int i;
		for(i=0;i<s.length();i++) {
			if(i==0&&s.charAt(i)=='-')
				continue;
			if(s.charAt(i)<48||s.charAt(i)>57)
				break;
			//if()
		}
		//s.substring(0, endIndex)
		boolean b=false;
		if(i==s.length())
			b=true;
		return b;
	}
	
	/**
	 * 判断输入的字符串是否为浮点数
	 * @param s
	 * @return
	 */
	private static boolean isDouble(String s) {
		int i,count=0;
		for(i=0;i<s.length();i++) {
			if(i==0&&s.charAt(i)=='-')
				continue;
			if((s.charAt(i)<48||s.charAt(i)>57)&&(s.charAt(i)!='.'))
				break;
			if(s.charAt(i)=='.')
				count++;
		}
		boolean b=false;
		if(i==s.length()) {
			if(count<2)
				b=true;
		}
		return b;
	}
	
	/**
	 * 从键盘输入一个整数
	 * @return 输入的整数
	 */
	public static int inputInteger() {
		String s=sc.next();
		int t=0;
		if(isInteger(s))
			t=Integer.parseInt(s);
		return t;
	}
	
	/**
	 * 从键盘输入一个浮点数
	 * @return 输入的浮点数
	 */
	public static double inputDouble() {
		String s=sc.next();
		double t=0;
		if(isDouble(s))
			t=Double.parseDouble(s);
		return t;
	}
	
	/**
	 * 从键盘输入一个字符串
	 * @return 输入的字符串
	 */
	public static String inputString() {
		String s=sc.next();
		return s;
	}
}
