/** ������
 * ��ʾ2~100�ڵ�������ÿ��5��
 * @author user
 */
public class PrimeTest {
	/** �ж���������Ƿ�Ϊ����
	 * @param n �������жϵĲ���
	 * @return �Ƿ�Ϊ������trueΪ������false��������
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
		// TODO �Զ����ɵķ������
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
