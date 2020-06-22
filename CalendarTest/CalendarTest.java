import java.util.*;

/** ��ʾϵͳ������������
 * @author user
 */
public class CalendarTest {
	/** ��ӡ�������е��·� */
	public static String months[]={
			"","January","February","March","April","May","June","July",
			"August","September","October","November","December"
		};
	
	/** ��ӡ�������е����� */
	public static String weekdays[] = { "Sun","Mon","Tue","Wed","Thu","Fri","Sat" };
	
	private Calendar c=Calendar.getInstance();
	private int year;
	private int month;
	private int day;
	private String dayOfWeek;// ���ڼ�
	
	/** ���������ʽ */
	private Formatter f=new Formatter(System.out);
		
	/** CalendarTest���޲ι��췽��
	 * ȡϵͳ��ǰ��������CalendarTest�����
	 */
	public CalendarTest() {
		year=c.get(Calendar.YEAR);
		month=c.get(Calendar.MONTH)+1;
		day=c.get(Calendar.DAY_OF_MONTH);
		dayOfWeek=weekdays[calculateDays()%7];
		// ��1��1��1�գ�����һ����ָ����������������7������Ϊ����Ϊ���ڼ�������Ϊ0Ϊ������
	}
		
	/** MyCalendarTest���вι��췽��
	 * ȡ�������������CalendarTest�����
	 * @param y ��������
	 * @param m ������·�
	 * @param d ������·ݵĵڼ���
	 * @throws Exception ����������ڲ��Ϸ�ʱ�����׳��쳣
	 */
	public CalendarTest(int y,int m,int d) throws Exception {
		if ((m == 4 || m == 6 || m == 9 || m == 11) && (d >= 31)) 
		{
			throw new Exception("Out of date!");
		}
		if ((m == 2) && (((isLeapYear(y) == true) && (d >= 30)) || (isLeapYear(y) == false) && (d >= 29))) 
		{
			throw new Exception("Out of date!");
		}
		if (d < 1 || d>31||m<1||m>12||y<1) 
		{
			throw new Exception("Out of date!");
		}
		year=y;
		month=m;
		day=d;
		dayOfWeek=weekdays[calculateDays()%7]; 
	}
		
	public int getYear() { return year; }
	public int getMonth() { return month; }
	public int getDay() { return day; }
	public String getWeekDay() { return dayOfWeek; }
	
	/** �ж�����Ƿ�Ϊ����
	 * @param y ���жϵ����
	 * @return trueΪ���꣬false��������
	 */
	public static boolean isLeapYear(int y) {
		if ((y % 4 == 0 && y % 100 != 0) || (y % 400 == 0)) {
			return true;
		}
		return false;
	}
	
	/** �����1��1��1�գ�����һ����ָ������������
	 * @return ��1��1��1�գ�����һ����ָ������������
	 */
	public int calculateDays() {
		int sum = 0;
		int i;
		for (i = 1; i < year; i++) {
			if (isLeapYear(i)==true) {
				sum += 366;
			}
			else {
				sum += 365;
			}
		}
		for (i = 1; i < month; i++){
			if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10||i==12) {
				sum += 31;
			}
			if (i == 4 || i == 6 || i == 9 || i == 11) {
				sum += 30;
			}
			if (i == 2) {
				if (isLeapYear(year)==true) {
					sum += 29;
				}
				else {
					sum += 28;
				}
			}
		}
		sum += day;
		return sum;
	}
		
	/** ��ӡ��ǰ���������
	 * @throws Exception ����������ڲ��Ϸ�ʱ�����׳��쳣
	 */
	public void printCalendar() throws Exception {
		System.out.println(year+" "+months[month]);
		int i,j=1;
		for (i = 1; i <= 39; i++) {
			System.out.print("-");
		}
		System.out.println();
		for(String w:weekdays) {
			f.format("%-6s",w);
		}
		System.out.println();
		CalendarTest cal=new CalendarTest(year,month,1);
		int monthDay=31;
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			monthDay = 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			monthDay = 30;
		}
		if (month == 2) {
			if (isLeapYear(year)==true) {
				monthDay = 29;
			}
			else {
				monthDay = 28;
			}
		}
		i = 1;
		while (i <= monthDay) {
			int k = 0;
			while (i == 1 && !(cal.dayOfWeek.equals(weekdays[k]))) {
				f.format("%3s"," ");
				f.format("%3s"," ");
				k++;
				j++;
			}
			f.format("%3d",i);
			if(j%7==0) {
				System.out.println();
			}
			else {
				f.format("%3s"," ");
			}
			i++;
			j++;
		}
		if ((j-1) % 7 != 0) {
			System.out.println();
		}
		for (i = 1; i <= 39; i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		try {
			CalendarTest calendar=new CalendarTest();
			calendar.printCalendar();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}

}


