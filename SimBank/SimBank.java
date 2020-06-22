import java.util.*;
import java.util.concurrent.*;
import java.math.*;

/**
 * ģ������
 * @author user
 */
public class SimBank {

	private CustomerQueue customers=new CustomerQueue(50); 
	private int totalMoney=(int)(10+Math.random()*10)*1000;//��������������
	private double totalServiceTime=0;//����˿���ʱ��
	private double totalTime=0;//��Ӫҵʱ��
	private boolean newCustomer=true;
	private boolean continueServe=true;
	
	/**
	 * ����ҵ������
	 * @author user
	 */
	private enum Status {
		DEPOSIT,WITHDRAW;
	}
	
	/**
	 * �˿���
	 * @author user
	 */
	public class Customer {
		private int balance;//�ʻ����
		private int money;//�ͻ�����ҵ����
		private int needServiceTime;//�ÿͻ��������ʱ�䣨��λms��
		private double realServiceTime;//�ÿͻ�ʵ���������ʱ�䣨��λmin��
		private int id;
		private Status status;//�ͻ�����ҵ������
		private boolean success=true;//�Ƿ�ɹ�����ҵ��
		
		public Customer(int b,int m,int t,int i,int s) {
			balance=b;
			money=m;
			id=i;
			needServiceTime=t;
			realServiceTime=needServiceTime/60;
			BigDecimal decimal=new BigDecimal(Double.toString(realServiceTime));
			realServiceTime=decimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			if(s==0) {
				status=Status.WITHDRAW;
			}
			else if(s==1) {
				status=Status.DEPOSIT;
			}
		}
		
		/**
		 * ��Ǯ
		 */
		public synchronized void deposit() {
			balance+=money;
		}
		
		/**
		 * ȡǮ
		 * @return trueΪȡǮ�ɹ���falseΪȡǮʧ�ܣ����㣩
		 */
		public synchronized boolean withdraw() {
			if(money>balance){
				success=false;
				return false;
			}
			balance-=money;
			return true;
		}
		
		public int getServiceTime() {
			return needServiceTime;
		}
		
		@Override
		public String toString() {
			StringBuffer s=new StringBuffer();
			s.append("idΪ"+id+"�Ŀͻ�������Ϣ��\n");
			s.append("----------------------\n");
			s.append("�ͻ�����ʱ�䣺"+realServiceTime+"min\n");
			s.append("�˻���"+balance+"Ԫ\n");
			s.append("����ҵ�����ͣ�"+status+"\n");
			s.append("����ҵ���"+money+"Ԫ\n");
			s.append("ҵ���Ƿ����ɹ���"+success+"\n");
			s.append("----------------------");
			return s.toString();
		}
	}
	
	/**
	 * �˿Ͷ���
	 * @author user
	 */
	private class CustomerQueue {
		private ArrayBlockingQueue<Customer> queue;
		private int maxQueue;
		
		public CustomerQueue(int m) {
			maxQueue=m;
			queue=new ArrayBlockingQueue<Customer>(maxQueue);
		}
		
		public int getSize() {
			return queue.size();
		}
		
		public int getMaxQueue() {
			return maxQueue;
		}
		
		/**
		 * �˿Ͷ�����������ǰ��Ĺ˿ͽ��ܷ���
		 * @return ������ǰ��Ĺ˿�
		 * @throws InterruptedException
		 */
		public Customer serve() throws InterruptedException {
			return queue.take();
		}
		
		/**
		 * �˿��Ŷӵȴ�����
		 * @param e ���ȴ��Ĺ˿�
		 * @throws InterruptedException
		 */
		public void enqueue(Customer e) throws InterruptedException {
			queue.put(e);
		}
		
		public boolean isEmpty() {
			return queue.size()==0;
		}
	}
	
	/**
	 * id��������
	 * @author user
	 */
	private static class idGenerator {
		private static int id=0;
		private static Random rand=new Random();
		public static int incCustomer() {
			return ++id;
		}
		public static int randomid() {
			return Math.abs(rand.nextInt());
		}
	}
	
	/**
	 * ����Ա��
	 * @author user
	 */
	public class Waiter extends Thread {
		
		/** ��ǰ����˿������� */
		private int serveCount=0;
		
		@Override
		public void run() {
			try {
				while(continueServe) {
					if(customers.isEmpty()) {
						continue;
					}
					Customer temp=customers.serve();//������ǰ��Ĺ˿ͽ��ܷ���
					TimeUnit.MILLISECONDS.sleep(temp.getServiceTime());//�ͻ�����ʱ��
					if(temp.status==Status.DEPOSIT) {
						temp.deposit();
						totalMoney+=temp.money;
					}
					else if(temp.status==Status.WITHDRAW) {
						temp.withdraw();
						if(temp.success) {
							totalMoney-=temp.money;
						}
						else {
							System.out.println("idΪ"+temp.id+"���˻����㣡����ȡ��ҵ��ʧ�ܣ�");
						}
					}
					System.out.println(temp);//���ɹ�����ҵ������ʾ�ɹ�����ҵ�����Ϣ��������ԭ������Ϣ
					
					synchronized(this) {
						serveCount++;
						totalServiceTime+=temp.realServiceTime;
						System.out.println("�Ѱ���ҵ��Ĺ˿�����"+serveCount);
						System.out.println("��ǰ�ȴ��˿�����"+customers.getSize());
						System.out.println("��ǰ������"+totalMoney+"Ԫ");
						System.out.println();
					}
				}
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("����Ա�°��ˣ���ʰ��ʰ׼���ؼ��ˣ�\n");
		}
	}
	
	/**
	 * �˿Ͱ���ҵ��
	 * @author user
	 */
	public class RandomCustomerGenerator extends Thread {
		Random rand=new Random();
		
		@Override
		public void run() {
			try {
				while(newCustomer) {
					TimeUnit.MILLISECONDS.sleep(1+rand.nextInt(2000));//��Ӫҵʱ����ÿ�����ʱ������¹˿Ͱ���ҵ��
					customers.enqueue(new Customer(1+rand.nextInt(2000),1+rand.nextInt(2000),1+rand.nextInt(2000)
							,idGenerator.randomid(),rand.nextInt(2)));//�¹˿��Ŷӣ��ȴ�����ҵ��
				}
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("���в��ٽӴ��µĹ˿ͣ�ʣ��˿ͼ������ܷ���");
			System.out.println();
		}
	}
	
	public SimBank() {
		try {
			System.out.println("���п�ʼӪҵ�ˣ�������"+totalMoney+"Ԫ\n");
			totalTime=35000.0/60.0;
			BigDecimal decimal=new BigDecimal(Double.toString(totalTime));
			totalTime=decimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			RandomCustomerGenerator r=new RandomCustomerGenerator();
			Waiter w=new Waiter();
			r.start();
			w.start();
			TimeUnit.SECONDS.sleep(18);//�Ӵ��¹˿�ʱ��
			newCustomer=false;
			//����ʣ��˿�ʱ�䣬�ڼ���ܳ�������ʣ��˿�ҵ�������ϵ��������ڼ���Ӫҵ�����
			TimeUnit.SECONDS.sleep(15);
			continueServe=false;
			TimeUnit.SECONDS.sleep(2);//����׼��������
			System.out.println("���й˿�ҵ�������ϣ����й����ˣ�");
			System.out.println("������"+totalMoney+"Ԫ");
			System.out.println("�ܷ���ʱ�䣺"+totalServiceTime+"min");
			System.out.println("��Ӫҵʱ�䣺"+totalTime+"min");
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		new SimBank();
	}

}
