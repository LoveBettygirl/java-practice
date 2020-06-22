package store;

import java.util.*;
import java.io.*;
import store.InputTools;

/**
 * CDstore��
 * @author user
 */
public class CDstore {

	/** ���л�Ա */
	private LinkedList<Member> members=new LinkedList<Member>();
	
	/** ����CD */
	private LinkedList<CD> Cds=new LinkedList<CD>();
	
	/** CD�������� */
	private double profit=0;
	
	/** CD����֧�� */
	private double expense=0;
	
	
	/**
	 * �����ࣨ��ʾ��Աע�����ںͽ������ڣ�
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
	 * id��������
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
	 * ��Ա��
	 * @author user
	 */
	public class Member {
		private int id;//��Աid
		private double money;//��Ա���
		private LinkedList<CD> have;//��Ա�ѹ����CD
		private LinkedList<CD> rent;//�����Ա��CD
		private MyDate date;//ע������
		
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
		 * ��ֵ
		 * @param m ����ֵ�Ľ��
		 */
		public void charge(double m) { 
			if(m<=0) {
				return;
			}
			money+=m; 
		}
		
		/**
		 * ����CD
		 * @param cd �������CD
		 */
		public void buyCD(CD cd) { 
			money-=cd.sellingPrice;
			have.add(cd);
		}
		
		/**
		 * ����CD
		 * @param cd �����õ�CD
		 */
		public void rentCD(CD cd) {
			money-=cd.sellingPrice/4;
			rent.add(cd);
		}
		
		/**
		 * �黹CD
		 * @param id ���黹CD��ID
		 * @return �黹��CD
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
			StringBuffer s=new StringBuffer("��ԱID��");
			s.append(id);
			s.append("\nע�����ڣ�");
			s.append(date);
			s.append("\n�ʻ���");
			s.append(money+"Ԫ");
			s.append("\n�ѹ����CD����");
			s.append(have.size());
			if(have.size()>0&&haveid!=null) {
				s.append("\n�ѹ����CD��ID�б�");
				s.append(Arrays.toString(haveid));
			}
			s.append("\n�������õ�CD����");
			s.append(rent.size());
			if(rent.size()>0&&rentid!=null) {
				s.append("\n�����õ�CD��ID�б�");
				s.append(Arrays.toString(rentid));
			}
			return s.toString();
		}
	}
	
	
	/**
	 * CD��
	 * @author user
	 */
	public class CD {
		private int id;//CD��id
		private String name;//CD��
		private String artist;//����
		private CDstate state;//CD��״̬
		private double purPrice;//����
		private double sellingPrice;//�ۼ�
		private MyDate date;//��������
		
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
			StringBuffer s=new StringBuffer("CD��ID��");
			s.append(id);
			s.append("\n�������ڣ�");
			s.append(date);
			s.append("\nCD����");
			s.append(name);
			s.append("\n��������");
			s.append(artist);
			s.append("\n�ۼۣ�");
			s.append(sellingPrice+"Ԫ");
			s.append("\n״̬��");
			s.append(state);
			return s.toString();
		}
	}
	
	/**
	 * CD������״̬��
	 * @author user
	 */
	private enum CDstate {
		AVAILABLE,RENTED,SOLD;
	}
	
	/** 
	 * ȡ�ø�id��Ӧ�Ļ�Ա�����Ҳ����򷵻�null
	 * @param id ������ID
	 * @return ��ID��Ӧ�Ļ�Ա
	 */
	private Member getMember(int id) {
		if(id<0) {
			return null;
		}
		Iterator<Member> i=members.iterator();//��������ĵ�����
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
	 * ȡ�ø�id��Ӧ��CD�����Ҳ����򷵻�null
	 * @param id ������ID
	 * @return ��ID��Ӧ��CD
	 */
	private CD getCD(int id) {
		if(id<0) {
			return null;
		}
		Iterator<CD> i=Cds.iterator();//��������ĵ�����
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
	 * ���ӻ�Ա����ֵ���
	 * @param money ����ֵ�Ľ��
	 */
	public void addMember(double money) {
		if(money<20) {
			System.out.println("�����������ӻ�Աʧ�ܣ����γ�ֵ����С��20Ԫ��");
			return;
		}
		String m1=Double.toString(money);
		int i;
		for(i=0;i<m1.length();i++) {
			if(m1.charAt(i)=='.')
				break;
		}
		if(i<m1.length()-1) {
			if(m1.length()-1-i>2) {//�������Ľ��С��λ��������λ��������λС��
				int t=(int)(money*100+0.5);
				money=((double)t)/100.0;
			}
		}
		Member temp=new Member(money);
		members.add(temp);
		System.out.println("��ӻ�Ա�ɹ����ʻ���Ϣ��\n"+temp);
	}
	
	/**
	 * ɾ����ID�Ļ�Ա
	 * @param id ������ԱID
	 */
	public void deleteMember(int id) {
		Member temp=getMember(id);
		if(temp==null) {
			System.out.println("IDΪ"+id+"���ʻ������ڣ�����ID�����Ƿ���ȷ��");
			return;
		}
		members.remove(temp);
		System.out.println("ɾ����Ա�ɹ���");
	}
	
	/**
	 * ����ָ������CD�����5�ţ�������ID�Ļ�Ա
	 * @param CDid ������CD��ID
	 * @param memid ������ԱID
	 */
	public void sell(Integer[] CDid,int memid) {
		Member tempm=getMember(memid);
		for(int i=0;i<CDid.length;i++) {
			CD tempc=getCD(CDid[i]);
			if(tempc==null) {
				System.out.println("�Ҳ���IDΪ"+CDid[i]+"��CD������ID�Ƿ�������ȷ��");
				continue;
			}
			else {
				if(tempm.money<tempc.sellingPrice) {
					System.out.println("�ʻ����㣡IDΪ"+CDid[i]+"��CD�޷����۸�IDΪ"+memid+"�Ļ�Ա�����ȳ�ֵ��");
					continue;
				}
				if(tempc.state==CDstate.RENTED) {
					System.out.println("IDΪ"+CDid[i]+"��CD���ڱ����ã��޷��۳���");
					continue;
				}
			}
			profit+=tempc.sellingPrice;
			tempc.state=CDstate.SOLD;
			tempm.buyCD(tempc);
			Cds.remove(tempc);
			System.out.println("IDΪ"+tempc.id+"��CD�ѳɹ��۳���ID"+tempm.id+"���ʻ���"+tempm.money+"Ԫ");
		}
	}
	
	/**
	 * ����ָ������CD�����5�ţ�������ID�Ļ�Ա
	 * @param CDid �������CD��ID
	 * @param memid ������ԱID
	 */
	public void rent(Integer[] CDid,int memid) {
		Member tempm=getMember(memid);
		for(int i=0;i<CDid.length;i++) {
			CD tempc=getCD(CDid[i]);
			if(tempc==null) {
				System.out.println("�Ҳ���IDΪ"+CDid[i]+"��CD������ID�Ƿ�������ȷ��");
				continue;
			}
			else {
				if(tempm.money<tempc.sellingPrice/4) {
					System.out.println("�ʻ����㣡IDΪ"+CDid[i]+"��CD�޷������IDΪ"+memid+"�Ļ�Ա�����ȳ�ֵ��");
					continue;
				}
				if(tempc.state==CDstate.RENTED) {
					System.out.println("IDΪ"+CDid[i]+"��CD���ڱ����ã��޷����⣡");
					continue;
				}
			}
			profit+=tempc.sellingPrice/4;//���ۼ�1/4�ļ۸����
			tempc.state=CDstate.RENTED;
			tempm.rentCD(tempc);
			System.out.println("IDΪ"+tempc.id+"��CD�ѳɹ����⣡ID"+tempm.id+"���ʻ���"+tempm.money+"Ԫ");
		}
	}
	
	/**
	 * ��Ա�黹CD
	 * @param CDid ���黹��CD��ID
	 * @param memid ������ԱID
	 */
	public void returnn(Integer[] CDid,int memid) {
		Member tempm=getMember(memid);
		for(int i=0;i<CDid.length;i++) {
			CD tempc=tempm.returnCD(CDid[i]);
			if(tempc==null) {
				System.out.println("IDΪ"+memid+"�Ļ�Աδ����IDΪ"+CDid[i]+"CD������ID�Ƿ�������ȷ��");
				continue;
			}
			tempc.state=CDstate.AVAILABLE;
			System.out.println("IDΪ"+CDid[i]+"CD�ѳɹ��黹��");
		}
	}
	
	/**
	 * ����
	 * @param addCD ���ӵ�CD
	 */
	public void addCDs(LinkedList<CD> addCD) {
		Cds.addAll(addCD);
		Iterator<CD> i=addCD.iterator();
		while(i.hasNext()) {
			expense+=i.next().purPrice;
		}
		System.out.println("�����ɹ���");
	}
	
	/**
	 * Ϊ��Ա�ʻ���ֵ
	 * @param id ����ֵ��ID
	 * @param money ����ֵ�Ľ��
	 */
	public void chargeMember(int id,double money) {
		Member temp=getMember(id);
		if(money<20) {
			System.out.println("���������󣡵��γ�ֵ����С��20Ԫ��");
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
			System.out.println("��ֵ�ɹ���ID"+temp.id+"���ʻ���"+temp.money+"Ԫ");
		}
	}
	
	/**
	 * ͳ�Ƶ��ڻ�Ա������CD�����������������CD��������֧�������뼰������Ϣ
	 */
	public void statistics() {
		System.out.println("��Ա����Ϊ��"+members.size());
		System.out.println("CD����Ϊ��"+Cds.size());
		System.out.println("������"+profit+"Ԫ");
		System.out.println("��֧����"+expense+"Ԫ");
		System.out.println("�����룺"+(profit-expense)+"Ԫ");
		if(members.size()>0) {
			System.out.println("��ǰ���ڵ�һλ��Ա��ע��ʱ�䣺"+members.getFirst().date);
			System.out.println("���»�Ա��ע��ʱ�䣺"+members.getLast().date);
		}
		if(Cds.size()>0) {
			System.out.println("�������CD�Ľ���ʱ�䣺"+Cds.getFirst().date);
			System.out.println("�������CD�Ľ���ʱ�䣺"+Cds.getLast().date);
		}
	}
	
	/**
	 * ��ȡ����ID�Ļ�Ա����Ϣ
	 * @param id ���鿴��Ա��ID
	 * @return �û�Ա������
	 */
	public Member getMemberImfor(int id) {
		Member temp=getMember(id);
		if(temp==null) {
			System.out.println("IDΪ"+id+"���ʻ������ڣ�����ID�Ƿ�������ȷ��");
		}
		else {
			System.out.println(temp);
		}
		return temp;
	}
	
	/**
	 * ��ȡ����ID��CD��Ϣ
	 * @param id ���鿴CD��ID
	 * @return ��CD������
	 */
	public CD getCDimfor(int id) {
		CD temp=getCD(id);
		if(temp==null) {
			System.out.println("IDΪ"+id+"��CD�����ڣ�����ID�Ƿ�������ȷ��");
		}
		else {
			System.out.println(temp);
		}
		return temp;
	}
	
	/**
	 * ���췽��
	 */
	public CDstore() {
		System.out.println("��ӭ�����ҵ�CD�������۵����ϵͳ��");
		System.out.println("����ϵͳʱ�䣺"+new Date());
	}
	
	/**
	 * �˳�ϵͳ
	 */
	public void exitSystem() {
		System.exit(0);
	}
	
	/**
	 * ����ϵͳ
	 */
	public void executeSystem() {
		System.out.println("----------��ѡ����----------");
		System.out.println("1.���ӻ�Ա");
		System.out.println("2.ɾ����Ա");
		System.out.println("3.�鿴��Ա��Ϣ");
		System.out.println("4.�鿴CD��Ϣ");
		System.out.println("5.����CD");
		System.out.println("6.����CD");
		System.out.println("7.����ͳ��");
		System.out.println("8.��Ա��ֵ");
		System.out.println("9.����");
		System.out.println("10.��Ա�黹CD");
		System.out.println("11.�˳�ϵͳ");
		System.out.println("-----------------------------");
		System.out.print("���빦�ܶ�Ӧ��ţ�");
		int a2=InputTools.inputInteger();
		switch(a2) {
		case 1:
			System.out.print("�������ֵ�Ľ����Ϊ�ʻ�����ע���ʻ���������20Ԫ����");
			double money=InputTools.inputDouble();
			addMember(money);
			writeMember();
			break;
		case 2:
			if(members.size()==0) {
				System.out.println("��ǰ�����޻�Ա���޷�ɾ����Ա��");
				break;
			}
			System.out.print("��������ɾ����Ա�ʻ���ID��");
			int t2=InputTools.inputInteger();
			deleteMember(t2);
			writeMember();
			break;
		case 3:
			if(members.size()==0) {
				System.out.println("��ǰ�����޻�Ա���޷��鿴��Ա��Ϣ��");
				break;
			}
			System.out.print("�������Ա�ʻ���ID�Բ鿴����Ϣ��");
			int t3=InputTools.inputInteger();
			getMemberImfor(t3);
			break;
		case 4:
			if(Cds.size()==0) {
				System.out.println("���û��CD���޷��鿴CD��Ϣ�����Ƚ�����");
				break;
			}
			System.out.print("������CD��ID�Բ鿴����Ϣ��");
			int t4=InputTools.inputInteger();
			getCDimfor(t4);
			break;
		case 5:
			if(members.size()==0) {
				System.out.println("��ǰ�����޻�Ա���޷����⣡");
				break;
			}
			if(Cds.size()==0) {
				System.out.println("���û��CD���޷����⣡���Ƚ�����");
				break;
			}
			System.out.print("������������Ļ�ԱID��");
			int id=InputTools.inputInteger();
			Member tempm=getMember(id);
			if(tempm==null) {
				System.out.println("IDΪ"+id+"���ʻ������ڣ�����ID�Ƿ�������ȷ��");
				break;
			}
			System.out.print("�������������IDΪ"+id+"�Ļ�Ա��CD��������1�ţ����5���Ҳ��������CD������");
			int num=InputTools.inputInteger();
			if(num<1||num>5) {
				System.out.println("������������CD������1�ţ����5���Ҳ��������CD����");
				break;
			}
			if(num>Cds.size()) {
				System.out.println("������������CD�����ܳ������CD����");
				break;
			}
			System.out.print("�������������CD��ID����һ���ظ�������CD����1����");
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
			System.out.println("��Ч����CD��Ϊ"+idTemp2.length);
			rent(idTemp2,id);
			break;
		case 6:
			if(members.size()==0) {
				System.out.println("��ǰ�����޻�Ա���޷����ۣ�");
				break;
			}
			if(Cds.size()==0) {
				System.out.println("���û��CD���޷����ۣ����Ƚ�����");
				break;
			}
			System.out.print("�����������۵Ļ�ԱID��");
			int id2=InputTools.inputInteger();
			Member tempm1=getMember(id2);
			if(tempm1==null) {
				System.out.println("IDΪ"+id2+"���ʻ������ڣ�����ID�Ƿ�������ȷ��");
				break;
			}
			System.out.print("�����������۸�IDΪ"+id2+"�Ļ�Ա��CD��������1�ţ����5���Ҳ��������CD������");
			int num1=InputTools.inputInteger();
			if(num1<1||num1>5) {
				System.out.println("������������CD������1�ţ����5���Ҳ��������CD����");
				break;
			}
			if(num1>Cds.size()) {
				System.out.println("������������CD�����ܳ������CD����");
				break;
			}
			System.out.print("�����������۵�CD��ID����һ���ظ������۵�CD����1����");
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
			System.out.println("��Ч����CD��Ϊ"+idTemp4.length);
			sell(idTemp4,id2);
			writeCD();
			break;
		case 7:
			statistics();
			break;
		case 8:
			if(members.size()==0) {
				System.out.println("��ǰ�����޻�Ա���޷�����Ա��ֵ��");
				break;
			}
			System.out.print("����������ֵ��Ա��ID��");
			int idm=InputTools.inputInteger();
			Member tempmmm=getMember(idm);
			if(tempmmm==null) {
				System.out.println("IDΪ"+idm+"���ʻ������ڣ�����ID�Ƿ�������ȷ��");
				break;
			}
			System.out.print("�������ֵ�Ľ�������20Ԫ����");
			double mmoney=InputTools.inputDouble();
			chargeMember(idm,mmoney);
			writeMember();
			break;
		case 9:
			System.out.print("������Ҫ������CD����");
			int t91=InputTools.inputInteger();
			if(t91<=0) {
				System.out.println("����������������CD������Ϊ1��");
				break;
			}
			LinkedList<CD> addCD=new LinkedList<CD>();
			int i5=0;
			while(i5<t91) {
				System.out.print("������CD���ۣ�");
				double t92=InputTools.inputInteger();
				if(t92<=0) {
					System.out.println("�����������Ľ��۱������0��");
					continue;
				}
				System.out.print("������CD�������롰*����Ϊ��������");
				String s93=InputTools.inputString();
				System.out.print("�����������������롰*����Ϊ��������");
				String s94=InputTools.inputString();
				System.out.print("������CDҪ�������ţ�����1���������ܽ���������");
				int t95=InputTools.inputInteger();
				if(t95<1||t95>(t91-i5)) {
					System.out.println("����Ľ�����������Ҫ��");
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
				System.out.println("��ǰ�����޻�Ա���޷��黹CD��");
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
				System.out.println("��ǰ���ڻ�Ա��δ��������CD���޷��黹CD��");
				break;
			}
			System.out.print("�������ԱID��");
			int t101=InputTools.inputInteger();
			Member tempmm=getMember(t101);
			if(tempmm==null) {
				System.out.println("IDΪ"+t101+"���ʻ������ڣ�����ID�Ƿ�������ȷ��");
				break;
			}
			if(tempmm.rent.size()==0) {
				System.out.println("IDΪ"+t101+"�Ļ�Աû���������õ�CD��");
				break;
			}
			System.out.print("���������黹��CD����");
			int num2=InputTools.inputInteger();
			if(num2<1) {
				System.out.println("������������CD������1�ţ�");
				break;
			}
			if(num2>tempmm.rent.size()) {
				if(Cds.size()==0) {
					System.out.println("�������IDΪ"+t101+"�Ļ�Աû������CD��");
				}
				else {
					System.out.println("������������CD�����ܳ���IDΪ"+t101+"�Ļ�Ա���õ�CD����");
				}
				break;
			}
			System.out.print("���������黹��CD��ID����һ���ظ���黹��CD����1����");
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
			System.out.println("��Ч����CD��Ϊ"+idTemp7.length);
			returnn(idTemp7,t101);
			break;
		case 11:
			exitSystem();
		default:
			System.out.println("����Ĺ��ܲ����ڣ����������룡");
		}
	}
	
	private void readMember() {
		try {
			String pathName=CDstore.class.getResource("member.txt").getPath();
			pathName=java.net.URLDecoder.decode(pathName,"utf-8");//�������·������
			FileReader fr=new FileReader(pathName);
			BufferedReader br= new BufferedReader(fr);
			String s=br.readLine();//��һ�е���Ϣû�����ã�������
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
			pathName=java.net.URLDecoder.decode(pathName,"utf-8");//�������·������
			FileReader fr=new FileReader(pathName);
			BufferedReader br= new BufferedReader(fr);
			String s=br.readLine();//��һ�е���Ϣû�����ã�������
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
			pathName=java.net.URLDecoder.decode(pathName,"utf-8");//�������·������
			PrintWriter pw=new PrintWriter(pathName);
			BufferedWriter bw=new BufferedWriter(pw);
			bw.write("���\t��\t��\t��\r\n");
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
			pathName=java.net.URLDecoder.decode(pathName,"utf-8");//�������·������
			PrintWriter pw=new PrintWriter(pathName);
			BufferedWriter bw=new BufferedWriter(pw);
			bw.write("CD��\t����\t��\t��\t��\t����\r\n");
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
		// TODO �Զ����ɵķ������
		CDstore store=new CDstore();
		store.readMember();
		store.readCD();
		while(true) {
			store.executeSystem();
			System.out.println();
		}
	}

}
