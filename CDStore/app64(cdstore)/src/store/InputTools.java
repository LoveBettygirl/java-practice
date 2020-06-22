package store;

import java.util.*;

/**
 * ���빤����
 * @author user
 */
public class InputTools {
	
	/** �ı�ɨ���������ڴӼ����������ݣ� */
	private static Scanner sc=new Scanner(System.in);
	
	/**
	 * �ж�������ַ����Ƿ�Ϊ����
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
	 * �ж�������ַ����Ƿ�Ϊ������
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
	 * �Ӽ�������һ������
	 * @return ���������
	 */
	public static int inputInteger() {
		String s=sc.next();
		int t=0;
		if(isInteger(s))
			t=Integer.parseInt(s);
		return t;
	}
	
	/**
	 * �Ӽ�������һ��������
	 * @return ����ĸ�����
	 */
	public static double inputDouble() {
		String s=sc.next();
		double t=0;
		if(isDouble(s))
			t=Double.parseDouble(s);
		return t;
	}
	
	/**
	 * �Ӽ�������һ���ַ���
	 * @return ������ַ���
	 */
	public static String inputString() {
		String s=sc.next();
		return s;
	}
}
