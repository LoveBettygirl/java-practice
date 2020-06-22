/** 素数类
 * 显示2~100内的素数，每行5个
 * @author user
 */
public class PrimeTest {
	/** 判断输入的数是否为素数
	 * @param n 欲进行判断的参数
	 * @return 是否为素数：true为素数，false不是素数
	 */
	public static boolean isPrime(int n) {
		if(n<=1) {
			return false;
		}
		else {
			if(n==2) {
				return true;
			}
			else {
				for(int i=2;i<n;i++) {
					if(n%i==0) {
						return false;
					}
				}
				return true;
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		int count=0;
		for(int i=2;i<=100;i++) {
			if(isPrime(i)==true) {
				System.out.print(i+" ");
				count++;
				if(count%5==0) {
					System.out.println();
				}
			}
		}
	}

}
