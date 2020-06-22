package store;

import java.util.*;
import java.io.*;
import store.InputTools;

/**
 * CDstore类
 * @author user
 */
public class CDstore {

	/** 所有会员 */
	private LinkedList<Member> members=new LinkedList<Member>();
	
	/** 所有CD */
	private LinkedList<CD> Cds=new LinkedList<CD>();
	
	/** CD店总利润 */
	private double profit=0;
	
	/** CD店总支出 */
	private double expense=0;
	
	
	/**
	 * 日期类（显示会员注册日期和进货日期）
	 * @author user
	 */
	private static class MyDate {
		private Calendar c=Calendar.getInstance();
		private int year;
		private int month;
		private int day;
		
		public MyDate() {
			year=c.get(Calendar.YEAR);
			month=c.get(Calendar.MONTH)+1;
			day=c.get(Calendar.DAY_OF_MONTH);
		}
		
		public MyDate(int y,int m,int d) {
			year=y;
			month=m;
			day=d;
		}
		
		@Override
		public String toString() {
			return year+"."+month+"."+day;
		}
	}
	
	
	/**
	 * id生成器类
	 * @author user
	 */
	private static class idGenerator {
		private static int memberid=0;
		private static int CDid=0;
		public static int incMember() {
			return ++memberid;
		}
		public static int incCD() {
			return ++CDid;
		}
	}
	
	
	/**
	 * 会员类
	 * @author user
	 */
	public class Member {
		private int id;//会员id
		private double money;//会员余额
		private LinkedList<CD> have;//会员已购买的CD
		private LinkedList<CD> rent;//租给会员的CD
		private MyDate date;//注册日期
		
		public Member(double m) {
			id=idGenerator.incMember();
			money=m;
			have=new LinkedList<CD>();
			rent=new LinkedList<CD>();
			date=new MyDate();
		}
		
		public Member(double m,int year,int month,int day) {
			id=idGenerator.incMember();
			money=m;
			have=new LinkedList<CD>();
			rent=new LinkedList<CD>();
			date=new MyDate(year,month,day);
		}
		
		/**
		 * 充值
		 * @param m 欲充值的金额
		 */
		public void charge(double m) { 
			if(m<=0) {
				return;
			}
			money+=m; 
		}
		
		/**
		 * 购买CD
		 * @param cd 欲购买的CD
		 */
		public void buyCD(CD cd) { 
			money-=cd.sellingPrice;
			have.add(cd);
		}
		
		/**
		 * 租用CD
		 * @param cd 欲租用的CD
		 */
		public void rentCD(CD cd) {
			money-=cd.sellingPrice/4;
			rent.add(cd);
		}
		
		/**
		 * 归还CD
		 * @param id 欲归还CD的ID
		 * @return 归还的CD
		 */
		public CD returnCD(int id) {
			CD temp=null;
			Iterator<CD> i=rent.iterator();
			while(i.hasNext()) {
				CD temp1=i.next();
				if(temp1.id==id) {
					temp=temp1;
					rent.remove(temp1);
					break;
				}
			}
			return temp;
		}
		
		@Override
		public String toString() {
			int[] haveid=null,rentid=null;
			if(have.size()>0) {
				haveid=new int[have.size()];
				Iterator<CD> i=have.iterator();
				int j=0;
				while(i.hasNext()) {
					haveid[j]=i.next().id;
					j++;
				}
			}
			if(rent.size()>0) {
				rentid=new int[rent.size()];
				Iterator<CD> i=rent.iterator();
				int j=0;
				while(i.hasNext()) {
					rentid[j]=i.next().id;
					j++;
				}
			}
			StringBuffer s=new StringBuffer("会员ID：");
			s.append(id);
			s.append("\n注册日期：");
			s.append(date);
			s.append("\n帐户余额：");
			s.append(money+"元");
			s.append("\n已购买的CD数：");
			s.append(have.size());
			if(have.size()>0&&haveid!=null) {
				s.append("\n已购买的CD的ID列表：");
				s.append(Arrays.toString(haveid));
			}
			s.append("\n正在租用的CD数：");
			s.append(rent.size());
			if(rent.size()>0&&rentid!=null) {
				s.append("\n已租用的CD的ID列表：");
				s.append(Arrays.toString(rentid));
			}
			return s.toString();
		}
	}
	
	
	/**
	 * CD类
	 * @author user
	 */
	public class CD {
		private int id;//CD的id
		private String name;//CD名
		private String artist;//作者
		private CDstate state;//CD的状态
		private double purPrice;//进价
		private double sellingPrice;//售价
		private MyDate date;//进货日期
		
		public CD(double pprice) {
			id=idGenerator.incCD();
			name="null";
			artist="anonymous";
			state=CDstate.AVAILABLE;
			purPrice=pprice;
			sellingPrice=pprice*1.2;
			date=new MyDate();
		}
		
		public CD(double pprice,int year,int month,int day) {
			id=idGenerator.incCD();
			name="null";
			artist="anonymous";
			state=CDstate.AVAILABLE;
			purPrice=pprice;
			sellingPrice=pprice*1.2;
			date=new MyDate(year,month,day);
		}
		
		public CD(String n,String a,double pprice) {
			id=idGenerator.incCD();
			name=n;
			artist=a;
			state=CDstate.AVAILABLE;
			purPrice=pprice;
			sellingPrice=pprice*1.2;
			date=new MyDate();
		}
		
		public CD(String n,String a,double pprice,int year,int month,int day) {
			id=idGenerator.incCD();
			name=n;
			artist=a;
			state=CDstate.AVAILABLE;
			purPrice=pprice;
			sellingPrice=pprice*1.2;
			date=new MyDate(year,month,day);
		}
		
		public CD(String n,double pprice) {
			id=idGenerator.incCD();
			name=n;
			artist="anonymous";
			state=CDstate.AVAILABLE;
			purPrice=pprice;
			sellingPrice=pprice*1.2;
			date=new MyDate();
		}
		
		public CD(String n,double pprice,int year,int month,int day) {
			id=idGenerator.incCD();
			name=n;
			artist="anonymous";
			state=CDstate.AVAILABLE;
			purPrice=pprice;
			sellingPrice=pprice*1.2;
			date=new MyDate(year,month,day);
		}
		
		public CD(String a,double pprice,String a2) {
			id=idGenerator.incCD();
			name="null";
			artist=a;
			state=CDstate.AVAILABLE;
			purPrice=pprice;
			sellingPrice=pprice*1.2;
			date=new MyDate();
		}
		
		public CD(String a,double pprice,int year,int month,int day,String a2) {
			id=idGenerator.incCD();
			name="null";
			artist=a;
			state=CDstate.AVAILABLE;
			purPrice=pprice;
			sellingPrice=pprice*1.2;
			date=new MyDate(year,month,day);
		}
		
		@Override
		public String toString() {
			StringBuffer s=new StringBuffer("CD的ID：");
			s.append(id);
			s.append("\n进货日期：");
			s.append(date);
			s.append("\nCD名：");
			s.append(name);
			s.append("\n作者名：");
			s.append(artist);
			s.append("\n售价：");
			s.append(sellingPrice+"元");
			s.append("\n状态：");
			s.append(state);
			return s.toString();
		}
	}
	
	/**
	 * CD所处的状态，
	 * @author user
	 */
	private enum CDstate {
		AVAILABLE,RENTED,SOLD;
	}
	
	/** 
	 * 取得该id对应的会员，若找不到则返回null
	 * @param id 给定的ID
	 * @return 该ID对应的会员
	 */
	private Member getMember(int id) {
		if(id<0) {
			return null;
		}
		Iterator<Member> i=members.iterator();//遍历链表的迭代器
		Member temp=null;
		while(i.hasNext()) {
			Member temp1=i.next();
			if(temp1.id==id) {
				temp=temp1;
				break;
			}
		}
		return temp;
	}
	
	/** 
	 * 取得该id对应的CD，若找不到则返回null
	 * @param id 给定的ID
	 * @return 该ID对应的CD
	 */
	private CD getCD(int id) {
		if(id<0) {
			return null;
		}
		Iterator<CD> i=Cds.iterator();//遍历链表的迭代器
		CD temp=null;
		while(i.hasNext()) {
			CD temp1=i.next();
			if(temp1.id==id) {
				temp=temp1;
				break;
			}
		}
		return temp;
	}
	
	/**
	 * 增加会员并充值余额
	 * @param money 欲充值的金额
	 */
	public void addMember(double money) {
		if(money<20) {
			System.out.println("输入金额错误！添加会员失败！单次充值金额不能小于20元！");
			return;
		}
		String m1=Double.toString(money);
		int i;
		for(i=0;i<m1.length();i++) {
			if(m1.charAt(i)=='.')
				break;
		}
		if(i<m1.length()-1) {
			if(m1.length()-1-i>2) {//如果输入的金额小数位数超过两位，则保留两位小数
				int t=(int)(money*100+0.5);
				money=((double)t)/100.0;
			}
		}
		Member temp=new Member(money);
		members.add(temp);
		System.out.println("添加会员成功！帐户信息：\n"+temp);
	}
	
	/**
	 * 删除此ID的会员
	 * @param id 给定会员ID
	 */
	public void deleteMember(int id) {
		Member temp=getMember(id);
		if(temp==null) {
			System.out.println("ID为"+id+"的帐户不存在！请检查ID输入是否正确！");
			return;
		}
		members.remove(temp);
		System.out.println("删除会员成功！");
	}
	
	/**
	 * 出售指定数量CD（最多5张）给给定ID的会员
	 * @param CDid 欲出租CD的ID
	 * @param memid 给定会员ID
	 */
	public void sell(Integer[] CDid,int memid) {
		Member tempm=getMember(memid);
		for(int i=0;i<CDid.length;i++) {
			CD tempc=getCD(CDid[i]);
			if(tempc==null) {
				System.out.println("找不到ID为"+CDid[i]+"的CD，请检查ID是否输入正确！");
				continue;
			}
			else {
				if(tempm.money<tempc.sellingPrice) {
					System.out.println("帐户余额不足！ID为"+CDid[i]+"的CD无法销售给ID为"+memid+"的会员！请先充值！");
					continue;
				}
				if(tempc.state==CDstate.RENTED) {
					System.out.println("ID为"+CDid[i]+"的CD正在被租用，无法售出！");
					continue;
				}
			}
			profit+=tempc.sellingPrice;
			tempc.state=CDstate.SOLD;
			tempm.buyCD(tempc);
			Cds.remove(tempc);
			System.out.println("ID为"+tempc.id+"的CD已成功售出！ID"+tempm.id+"的帐户余额："+tempm.money+"元");
		}
	}
	
	/**
	 * 出租指定数量CD（最多5张）给给定ID的会员
	 * @param CDid 欲出租的CD的ID
	 * @param memid 给定会员ID
	 */
	public void rent(Integer[] CDid,int memid) {
		Member tempm=getMember(memid);
		for(int i=0;i<CDid.length;i++) {
			CD tempc=getCD(CDid[i]);
			if(tempc==null) {
				System.out.println("找不到ID为"+CDid[i]+"的CD，请检查ID是否输入正确！");
				continue;
			}
			else {
				if(tempm.money<tempc.sellingPrice/4) {
					System.out.println("帐户余额不足！ID为"+CDid[i]+"的CD无法出租给ID为"+memid+"的会员！请先充值！");
					continue;
				}
				if(tempc.state==CDstate.RENTED) {
					System.out.println("ID为"+CDid[i]+"的CD正在被租用，无法出租！");
					continue;
				}
			}
			profit+=tempc.sellingPrice/4;//以售价1/4的价格出租
			tempc.state=CDstate.RENTED;
			tempm.rentCD(tempc);
			System.out.println("ID为"+tempc.id+"的CD已成功出租！ID"+tempm.id+"的帐户余额："+tempm.money+"元");
		}
	}
	
	/**
	 * 会员归还CD
	 * @param CDid 欲归还的CD的ID
	 * @param memid 给定会员ID
	 */
	public void returnn(Integer[] CDid,int memid) {
		Member tempm=getMember(memid);
		for(int i=0;i<CDid.length;i++) {
			CD tempc=tempm.returnCD(CDid[i]);
			if(tempc==null) {
				System.out.println("ID为"+memid+"的会员未租用ID为"+CDid[i]+"CD，请检查ID是否输入正确！");
				continue;
			}
			tempc.state=CDstate.AVAILABLE;
			System.out.println("ID为"+CDid[i]+"CD已成功归还！");
		}
	}
	
	/**
	 * 进货
	 * @param addCD 增加的CD
	 */
	public void addCDs(LinkedList<CD> addCD) {
		Cds.addAll(addCD);
		Iterator<CD> i=addCD.iterator();
		while(i.hasNext()) {
			expense+=i.next().purPrice;
		}
		System.out.println("进货成功！");
	}
	
	/**
	 * 为会员帐户充值
	 * @param id 欲充值的ID
	 * @param money 欲充值的金额
	 */
	public void chargeMember(int id,double money) {
		Member temp=getMember(id);
		if(money<20) {
			System.out.println("金额输入错误！单次充值金额不得小于20元！");
			return;
		}
		else {
			String m1=Double.toString(money);
			int i;
			for(i=0;i<m1.length();i++) {
				if(m1.charAt(i)=='.')
					break;
			}
			if(i<m1.length()-1) {
				if(m1.length()-1-i>2) {
					int t=(int)(money*100+0.5);
					money=((double)t)/100.0;
				}
			}
			temp.charge(money);
			System.out.println("充值成功！ID"+temp.id+"的帐户余额："+temp.money+"元");
		}
	}
	
	/**
	 * 统计店内会员总数和CD总数（包括被出租的CD）、利润、支出、收入及其他信息
	 */
	public void statistics() {
		System.out.println("会员总数为："+members.size());
		System.out.println("CD总数为："+Cds.size());
		System.out.println("总利润："+profit+"元");
		System.out.println("总支出："+expense+"元");
		System.out.println("总收入："+(profit-expense)+"元");
		if(members.size()>0) {
			System.out.println("当前店内第一位会员的注册时间："+members.getFirst().date);
			System.out.println("最新会员的注册时间："+members.getLast().date);
		}
		if(Cds.size()>0) {
			System.out.println("库存最早CD的进货时间："+Cds.getFirst().date);
			System.out.println("库存最新CD的进货时间："+Cds.getLast().date);
		}
	}
	
	/**
	 * 获取给定ID的会员的信息
	 * @param id 欲查看会员的ID
	 * @return 该会员的引用
	 */
	public Member getMemberImfor(int id) {
		Member temp=getMember(id);
		if(temp==null) {
			System.out.println("ID为"+id+"的帐户不存在！请检查ID是否输入正确！");
		}
		else {
			System.out.println(temp);
		}
		return temp;
	}
	
	/**
	 * 获取给定ID的CD信息
	 * @param id 欲查看CD的ID
	 * @return 该CD的引用
	 */
	public CD getCDimfor(int id) {
		CD temp=getCD(id);
		if(temp==null) {
			System.out.println("ID为"+id+"的CD不存在！请检查ID是否输入正确！");
		}
		else {
			System.out.println(temp);
		}
		return temp;
	}
	
	/**
	 * 构造方法
	 */
	public CDstore() {
		System.out.println("欢迎进入我的CD出租销售店管理系统！");
		System.out.println("进入系统时间："+new Date());
	}
	
	/**
	 * 退出系统
	 */
	public void exitSystem() {
		System.exit(0);
	}
	
	/**
	 * 运行系统
	 */
	public void executeSystem() {
		System.out.println("----------请选择功能----------");
		System.out.println("1.增加会员");
		System.out.println("2.删除会员");
		System.out.println("3.查看会员信息");
		System.out.println("4.查看CD信息");
		System.out.println("5.出租CD");
		System.out.println("6.销售CD");
		System.out.println("7.销售统计");
		System.out.println("8.会员充值");
		System.out.println("9.进货");
		System.out.println("10.会员归还CD");
		System.out.println("11.退出系统");
		System.out.println("-----------------------------");
		System.out.print("输入功能对应标号：");
		int a2=InputTools.inputInteger();
		switch(a2) {
		case 1:
			System.out.print("请输入充值的金额作为帐户余额，以注册帐户（不少于20元）：");
			double money=InputTools.inputDouble();
			addMember(money);
			writeMember();
			break;
		case 2:
			if(members.size()==0) {
				System.out.println("当前店内无会员，无法删除会员！");
				break;
			}
			System.out.print("请输入欲删除会员帐户的ID：");
			int t2=InputTools.inputInteger();
			deleteMember(t2);
			writeMember();
			break;
		case 3:
			if(members.size()==0) {
				System.out.println("当前店内无会员，无法查看会员信息！");
				break;
			}
			System.out.print("请输入会员帐户的ID以查看其信息：");
			int t3=InputTools.inputInteger();
			getMemberImfor(t3);
			break;
		case 4:
			if(Cds.size()==0) {
				System.out.println("库存没有CD，无法查看CD信息！请先进货！");
				break;
			}
			System.out.print("请输入CD的ID以查看其信息：");
			int t4=InputTools.inputInteger();
			getCDimfor(t4);
			break;
		case 5:
			if(members.size()==0) {
				System.out.println("当前店内无会员，无法出租！");
				break;
			}
			if(Cds.size()==0) {
				System.out.println("库存没有CD，无法出租！请先进货！");
				break;
			}
			System.out.print("请输入欲出租的会员ID：");
			int id=InputTools.inputInteger();
			Member tempm=getMember(id);
			if(tempm==null) {
				System.out.println("ID为"+id+"的帐户不存在！请检查ID是否输入正确！");
				break;
			}
			System.out.print("请输入欲出租给ID为"+id+"的会员的CD数（最少1张，最多5张且不超过库存CD数）：");
			int num=InputTools.inputInteger();
			if(num<1||num>5) {
				System.out.println("输入错误！输入的CD数最少1张，最多5张且不超过库存CD数！");
				break;
			}
			if(num>Cds.size()) {
				System.out.println("输入错误！输入的CD数不能超出库存CD数！");
				break;
			}
			System.out.print("请输入欲出租的CD的ID（有一个重复则出租的CD数减1）：");
			Integer[] idTemp1=new Integer[num];
			int i1;
			for(i1=0;i1<idTemp1.length;i1++) {
				idTemp1[i1]=InputTools.inputInteger();
			}
			LinkedList<Integer> occur=new LinkedList<Integer>();
			int j;
			for(i1=0;i1<idTemp1.length;i1++) {
				for(j=0;j<i1;j++) {
					if(idTemp1[j]==idTemp1[i1])
						break;
				}
				if(j==i1) {
					occur.add(idTemp1[i1]);
				}
			}
			Integer[] idTemp2=occur.toArray(new Integer[occur.size()]);
			System.out.println("有效输入CD数为"+idTemp2.length);
			rent(idTemp2,id);
			break;
		case 6:
			if(members.size()==0) {
				System.out.println("当前店内无会员，无法销售！");
				break;
			}
			if(Cds.size()==0) {
				System.out.println("库存没有CD，无法销售！请先进货！");
				break;
			}
			System.out.print("请输入欲销售的会员ID：");
			int id2=InputTools.inputInteger();
			Member tempm1=getMember(id2);
			if(tempm1==null) {
				System.out.println("ID为"+id2+"的帐户不存在！请检查ID是否输入正确！");
				break;
			}
			System.out.print("请输入欲销售给ID为"+id2+"的会员的CD数（最少1张，最多5张且不超过库存CD数）：");
			int num1=InputTools.inputInteger();
			if(num1<1||num1>5) {
				System.out.println("输入错误！输入的CD数最少1张，最多5张且不超过库存CD数！");
				break;
			}
			if(num1>Cds.size()) {
				System.out.println("输入错误！输入的CD数不能超出库存CD数！");
				break;
			}
			System.out.print("请输入欲销售的CD的ID（有一个重复则销售的CD数减1）：");
			Integer[] idTemp3=new Integer[num1];
			int i3;
			for(i3=0;i3<idTemp3.length;i3++) {
				idTemp3[i3]=InputTools.inputInteger();
			}
			LinkedList<Integer> occur1=new LinkedList<Integer>();
			int j1;
			for(i3=0;i3<idTemp3.length;i3++) {
				for(j1=0;j1<i3;j1++) {
					if(idTemp3[j1]==idTemp3[i3])
						break;
				}
				if(j1==i3) {
					occur1.add(idTemp3[i3]);
				}
			}
			Integer[] idTemp4=occur1.toArray(new Integer[occur1.size()]);
			System.out.println("有效输入CD数为"+idTemp4.length);
			sell(idTemp4,id2);
			writeCD();
			break;
		case 7:
			statistics();
			break;
		case 8:
			if(members.size()==0) {
				System.out.println("当前店内无会员，无法给会员充值！");
				break;
			}
			System.out.print("请输入欲充值会员的ID：");
			int idm=InputTools.inputInteger();
			Member tempmmm=getMember(idm);
			if(tempmmm==null) {
				System.out.println("ID为"+idm+"的帐户不存在！请检查ID是否输入正确！");
				break;
			}
			System.out.print("请输入充值的金额（不少于20元）：");
			double mmoney=InputTools.inputDouble();
			chargeMember(idm,mmoney);
			writeMember();
			break;
		case 9:
			System.out.print("请输入要进货的CD数：");
			int t91=InputTools.inputInteger();
			if(t91<=0) {
				System.out.println("输入错误！输入进货的CD数最少为1！");
				break;
			}
			LinkedList<CD> addCD=new LinkedList<CD>();
			int i5=0;
			while(i5<t91) {
				System.out.print("请输入CD进价：");
				double t92=InputTools.inputInteger();
				if(t92<=0) {
					System.out.println("输入错误！输入的进价必须大于0！");
					continue;
				}
				System.out.print("请输入CD名（输入“*”则为佚名）：");
				String s93=InputTools.inputString();
				System.out.print("请输入作者名（输入“*”则为佚名）：");
				String s94=InputTools.inputString();
				System.out.print("这样的CD要进货几张（最少1，最多等于总进货数）？");
				int t95=InputTools.inputInteger();
				if(t95<1||t95>(t91-i5)) {
					System.out.println("输入的进货数不符合要求！");
					continue;
				}
				int k;
				for(k=1;k<=t95;k++) {
					if(s93.equals("*")&&!(s94.equals("*"))) {
						addCD.add(new CD(s94,t92,s94));
					}
					else if(!(s93.equals("*"))&&s94.equals("*")) {
						addCD.add(new CD(s93,t92));
					}
					else if(s93.equals("*")&&s94.equals("*")) {
						addCD.add(new CD(t92));
					}
					else {
						addCD.add(new CD(s93,s94,t92));
					}
				}
				i5+=t95;
			}
			addCDs(addCD);
			writeCD();
			break;
		case 10:
			if(members.size()==0) {
				System.out.println("当前店内无会员，无法归还CD！");
				break;
			}
			Iterator<Member> iii=members.iterator();
			int ccount=0;
			while(iii.hasNext()) {
				Member temp=iii.next();
				if(temp.rent.size()==0) {
					ccount++;
				}
			}
			if(ccount==members.size()) {
				System.out.println("当前店内会员都未正在租用CD，无法归还CD！");
				break;
			}
			System.out.print("请输入会员ID：");
			int t101=InputTools.inputInteger();
			Member tempmm=getMember(t101);
			if(tempmm==null) {
				System.out.println("ID为"+t101+"的帐户不存在！请检查ID是否输入正确！");
				break;
			}
			if(tempmm.rent.size()==0) {
				System.out.println("ID为"+t101+"的会员没有正在租用的CD！");
				break;
			}
			System.out.print("请输入欲归还的CD数：");
			int num2=InputTools.inputInteger();
			if(num2<1) {
				System.out.println("输入错误！输入的CD数最少1张！");
				break;
			}
			if(num2>tempmm.rent.size()) {
				if(Cds.size()==0) {
					System.out.println("输入错误！ID为"+t101+"的会员没有租用CD！");
				}
				else {
					System.out.println("输入错误！输入的CD数不能超出ID为"+t101+"的会员租用的CD数！");
				}
				break;
			}
			System.out.print("请输入欲归还的CD的ID（有一个重复则归还的CD数减1）：");
			Integer[] idTemp6=new Integer[num2];
			int i4;
			for(i4=0;i4<idTemp6.length;i4++) {
				idTemp6[i4]=InputTools.inputInteger();
			}
			LinkedList<Integer> occur2=new LinkedList<Integer>();
			int j2;
			for(i4=0;i4<idTemp6.length;i4++) {
				for(j2=0;j2<i4;j2++) {
					if(idTemp6[j2]==idTemp6[i4])
						break;
				}
				if(j2==i4) {
					occur2.add(idTemp6[i4]);
				}
			}
			Integer[] idTemp7=occur2.toArray(new Integer[occur2.size()]);
			System.out.println("有效输入CD数为"+idTemp7.length);
			returnn(idTemp7,t101);
			break;
		case 11:
			exitSystem();
		default:
			System.out.println("输入的功能不存在！请重新输入！");
		}
	}
	
	private void readMember() {
		try {
			String pathName=CDstore.class.getResource("member.txt").getPath();
			pathName=java.net.URLDecoder.decode(pathName,"utf-8");//解决中文路径问题
			FileReader fr=new FileReader(pathName);
			BufferedReader br= new BufferedReader(fr);
			String s=br.readLine();//第一行的信息没有作用，故跳过
			String mo=null,y=null,m=null,d=null;
			int year,month,day;
			year=month=day=0;
			double money=0;
			while((s=br.readLine())!=null) {
				int i,count=0,temp=0;
				for(i=0;i<s.length();i++) {
					if(s.charAt(i)=='\t') {
						count++;
						if(count==1) {
							mo=s.substring(0, i);
							money=Double.parseDouble(mo);
							temp=i+1;
						}
						else if(count==2) {
							y=s.substring(temp, i);
							year=Integer.parseInt(y);
							temp=i+1;
						}
						else if(count==3) {
							m=s.substring(temp, i);
							month=Integer.parseInt(m);
							d=s.substring(i+1, s.length());
							day=Integer.parseInt(d);
						}
					}
				}
				members.add(new Member(money,year,month,day));
			}
			br.close();
			fr.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void readCD() {
		try {
			String pathName=CDstore.class.getResource("CD.txt").getPath();
			pathName=java.net.URLDecoder.decode(pathName,"utf-8");//解决中文路径问题
			FileReader fr=new FileReader(pathName);
			BufferedReader br= new BufferedReader(fr);
			String s=br.readLine();//第一行的信息没有作用，故跳过
			String name=null;
			String artist=null;
			String y=null;
			String m=null;
			String d=null;
			String p=null;
			int year,month,day;
			year=month=day=0;
			double pprice=0;
			while((s=br.readLine())!=null) {
				int i,count=0,temp=0;
				for(i=0;i<s.length();i++) {
					if(s.charAt(i)=='\t') {
						count++;
						if(count==1) {
							name=s.substring(0, i);
							temp=i+1;
						}
						else if(count==2) {
							artist=s.substring(temp, i);
							temp=i+1;
						}
						else if(count==3) {
							y=s.substring(temp, i);
							year=Integer.parseInt(y);
							temp=i+1;
						}
						else if(count==4) {
							m=s.substring(temp, i);
							month=Integer.parseInt(m);
							temp=i+1;
						}
						else if(count==5) {
							d=s.substring(temp, i);
							day=Integer.parseInt(d);
							p=s.substring(i+1, s.length());
							pprice=Double.parseDouble(p);
						}
					}
				}
				if(name.equals("*")&&!(artist.equals("*"))) {
					Cds.add(new CD(artist,pprice,year,month,day,artist));
				}
				else if(!(name.equals("*"))&&artist.equals("*")) {
					Cds.add(new CD(name,pprice,year,month,day));
				}
				else if(name.equals("*")&&artist.equals("*")) {
					Cds.add(new CD(pprice,year,month,day));
				}
				else {
					Cds.add(new CD(name,artist,pprice,year,month,day));
				}
				expense+=Cds.getLast().purPrice;
				
			}
			br.close();
			fr.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void writeMember() {
		try {
			String pathName=CDstore.class.getResource("member.txt").getPath();
			pathName=java.net.URLDecoder.decode(pathName,"utf-8");//解决中文路径问题
			PrintWriter pw=new PrintWriter(pathName);
			BufferedWriter bw=new BufferedWriter(pw);
			bw.write("余额\t年\t月\t日\r\n");
			StringBuffer s;
			Iterator<Member> i=members.iterator();
			while(i.hasNext()) {
				Member temp=i.next();
				s=new StringBuffer(Double.toString(temp.money));
				s.append("\t");
				s.append(Integer.toString(temp.date.year));
				s.append("\t");
				s.append(Integer.toString(temp.date.month));
				s.append("\t");
				s.append(Integer.toString(temp.date.day));
				if(i.hasNext()) {
					s.append("\r\n");
				}
				bw.write(s.toString());
			}
			bw.flush();
			bw.close();
			pw.flush();
			pw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeCD() {
		try {
			String pathName=CDstore.class.getResource("CD.txt").getPath();
			pathName=java.net.URLDecoder.decode(pathName,"utf-8");//解决中文路径问题
			PrintWriter pw=new PrintWriter(pathName);
			BufferedWriter bw=new BufferedWriter(pw);
			bw.write("CD名\t作者\t年\t月\t日\t进价\r\n");
			StringBuffer s;
			Iterator<CD> i=Cds.iterator();
			while(i.hasNext()) {
				CD temp=i.next();
				s=new StringBuffer();
				if(temp.name.equals("null")) {
					s.append("*");
				}
				else {
					s.append(temp.name);
				}
				s.append("\t");
				if(temp.artist.equals("anonymous")) {
					s.append("*");
				}
				else {
					s.append(temp.artist);
				}
				s.append("\t");
				s.append(Integer.toString(temp.date.year));
				s.append("\t");
				s.append(Integer.toString(temp.date.month));
				s.append("\t");
				s.append(Integer.toString(temp.date.day));
				s.append("\t");
				s.append(Double.toString(temp.purPrice));
				if(i.hasNext()) {
					s.append("\r\n");
				}
				bw.write(s.toString());
			}
			bw.flush();
			bw.close();
			pw.flush();
			pw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args 
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		CDstore store=new CDstore();
		store.readMember();
		store.readCD();
		while(true) {
			store.executeSystem();
			System.out.println();
		}
	}

}
