import java.util.*;
import java.util.concurrent.*;
import java.math.*;

/**
 * 模拟银行
 * @author user
 */
public class SimBank {

	private CustomerQueue customers=new CustomerQueue(50); 
	private int totalMoney=(int)(10+Math.random()*10)*1000;//随机生成银行余额
	private double totalServiceTime=0;//服务顾客总时间
	private double totalTime=0;//总营业时间
	private boolean newCustomer=true;
	private boolean continueServe=true;
	
	/**
	 * 办理业务类型
	 * @author user
	 */
	private enum Status {
		DEPOSIT,WITHDRAW;
	}
	
	/**
	 * 顾客类
	 * @author user
	 */
	public class Customer {
		private int balance;//帐户余额
		private int money;//客户办理业务金额
		private int needServiceTime;//该客户所需服务时间（单位ms）
		private double realServiceTime;//该客户实际所需服务时间（单位min）
		private int id;
		private Status status;//客户办理业务类型
		private boolean success=true;//是否成功办理业务
		
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
		 * 存钱
		 */
		public synchronized void deposit() {
			balance+=money;
		}
		
		/**
		 * 取钱
		 * @return true为取钱成功，false为取钱失败（余额不足）
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
			s.append("id为"+id+"的客户服务信息：\n");
			s.append("----------------------\n");
			s.append("客户服务时间："+realServiceTime+"min\n");
			s.append("账户余额："+balance+"元\n");
			s.append("办理业务类型："+status+"\n");
			s.append("办理业务金额："+money+"元\n");
			s.append("业务是否办理成功："+success+"\n");
			s.append("----------------------");
			return s.toString();
		}
	}
	
	/**
	 * 顾客队列
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
		 * 顾客队列中排在最前面的顾客接受服务
		 * @return 排在最前面的顾客
		 * @throws InterruptedException
		 */
		public Customer serve() throws InterruptedException {
			return queue.take();
		}
		
		/**
		 * 顾客排队等待服务
		 * @param e 欲等待的顾客
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
	 * id生成器类
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
	 * 服务员类
	 * @author user
	 */
	public class Waiter extends Thread {
		
		/** 当前服务顾客总人数 */
		private int serveCount=0;
		
		@Override
		public void run() {
			try {
				while(continueServe) {
					if(customers.isEmpty()) {
						continue;
					}
					Customer temp=customers.serve();//排在最前面的顾客接受服务
					TimeUnit.MILLISECONDS.sleep(temp.getServiceTime());//客户服务时间
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
							System.out.println("id为"+temp.id+"的账户余额不足！办理取款业务失败！");
						}
					}
					System.out.println(temp);//若成功办理业务则显示成功办理业务的信息，否则是原来的信息
					
					synchronized(this) {
						serveCount++;
						totalServiceTime+=temp.realServiceTime;
						System.out.println("已办理业务的顾客数："+serveCount);
						System.out.println("当前等待顾客数："+customers.getSize());
						System.out.println("当前银行余额："+totalMoney+"元");
						System.out.println();
					}
				}
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("服务员下班了！收拾收拾准备回家了！\n");
		}
	}
	
	/**
	 * 顾客办理业务
	 * @author user
	 */
	public class RandomCustomerGenerator extends Thread {
		Random rand=new Random();
		
		@Override
		public void run() {
			try {
				while(newCustomer) {
					TimeUnit.MILLISECONDS.sleep(1+rand.nextInt(2000));//在营业时间内每隔随机时间就有新顾客办理业务
					customers.enqueue(new Customer(1+rand.nextInt(2000),1+rand.nextInt(2000),1+rand.nextInt(2000)
							,idGenerator.randomid(),rand.nextInt(2)));//新顾客排队，等待办理业务
				}
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("银行不再接待新的顾客！剩余顾客继续接受服务！");
			System.out.println();
		}
	}
	
	public SimBank() {
		try {
			System.out.println("银行开始营业了！银行余额："+totalMoney+"元\n");
			totalTime=35000.0/60.0;
			BigDecimal decimal=new BigDecimal(Double.toString(totalTime));
			totalTime=decimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			RandomCustomerGenerator r=new RandomCustomerGenerator();
			Waiter w=new Waiter();
			r.start();
			w.start();
			TimeUnit.SECONDS.sleep(18);//接待新顾客时间
			newCustomer=false;
			//服务剩余顾客时间，期间可能出现所有剩余顾客业务办理完毕但银行仍在继续营业的情况
			TimeUnit.SECONDS.sleep(15);
			continueServe=false;
			TimeUnit.SECONDS.sleep(2);//银行准备关门了
			System.out.println("所有顾客业务办理完毕，银行关门了！");
			System.out.println("银行余额："+totalMoney+"元");
			System.out.println("总服务时间："+totalServiceTime+"min");
			System.out.println("总营业时间："+totalTime+"min");
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		new SimBank();
	}

}
