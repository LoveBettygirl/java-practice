/** �߶���
 * @author user
 */
public class MyLine {
	
	/** �߶ε������˵� */
	Point e1,e2;
	
	/** ���췽��
	 * @param p1 �߶ε�����һ���˵�
	 * @param p2 �߶ε�����һ���˵�
	 * @throws Exception ��������˵���ȣ�����ͬһ��λ�ã������׳��쳣
	 */
	public MyLine(Point p1,Point p2) throws Exception {
		e1=p1;
		e2=p2;
		if(e1.equals(e2)) {
			throw new Exception("e1 and e2 are at the same position!");
		}
		equation();
	}
	
	@Override
	public String toString() {
		return "l:e1("+e1.x+","+e1.y+"),e2("+e2.x+","+e2.y+")";
	}
	
	/** ���߶�����ֱ�ߵķ���a*x+b*y+c=0��ÿ��ϵ���������α����������� */
	private double[] equation() {
		double[] result=new double[3];
		if(e1.x==e2.x||e1.y==e2.y) {
			if(e1.x==e2.x) {
				result[0]=1;
				result[2]=-e1.x;
			}
			else if(e1.y==e2.y) {
				result[1]=1;
				result[2]=-e1.y;
			}
		}
		else {
			result[0]=1/(e2.x-e1.x);
			result[1]=1/(e1.y-e2.y);
			result[2]=e1.y/(e2.y-e1.y)-e1.x/(e2.x-e1.x);
		}
		return result;
	}
	
	/** ����߶��Ƿ�λ�ڵ�һ����
	 * @return true:�߶�λ�ڵ�һ���ޣ�false��֮
	 */
	public boolean check() {
		if(e1.x>0&&e1.y>0&&e2.x>0&&e2.y>0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/** ���߶εĳ��� 
	 * @return �߶εĳ���
	 */
	public double lineLength() {
		double sqr=(e2.x-e1.x)*(e2.x-e1.x)+(e2.y-e1.y)*(e2.y-e1.y);
		return Math.sqrt(sqr);
	}
	
	/** �ж������߶�����ֱ���Ƿ��ཻ
	 * @param l1 ��һ���߶�
	 * @return true����ֱ���ཻ��false��֮
	 */
	public boolean intersect(MyLine l1) {
		double[] r1=equation();
		double[] r2=l1.equation();
		if(r1[0]*r2[1]-r2[0]*r1[1]!=0) { // �ཻ
			return true;
		}
		else { // ƽ�л��غ�
			return false;
		}
	}
	
	/** ��һ�㵽���߶Σ����ӳ��ߣ��ľ��� 
	 * @param p �����ĵ�
	 * @return �õ㵽���߶Σ����ӳ��ߣ��ľ���
	 */
	public double pointDistance(Point p) {
		double[] r=equation();
		double d=(r[0]*p.x+r[1]*p.y+r[2])/Math.sqrt(r[0]*r[0]+r[1]*r[1]);
		return Math.abs(d);
	}
	
	/** �������߶�����ֱ�ߵĴ�ֱ����
	 * @param l1 ��һ���߶�
	 * @return �����߶�����ֱ�ߵĴ�ֱ���룬���ཻ���غϽ��Ϊ0.0
	 */
	public double lineDistance(MyLine l1) {
		if(intersect(l1)) {
			return 0.0;
		}
		else {
			double[] r1=equation();
			double[] r2=l1.equation();
			if(r1[0]==0||r1[1]==0) {
				return Math.abs(r1[2]-r2[2]);
			}
			double a=1,b=r1[1]/r1[0],c1=r1[2]/r1[0],c2=r2[2]/r2[0];
			double d=(c1-c2)/Math.sqrt(a*a+b*b);
			return Math.abs(d);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		try {
			MyLine l1=new MyLine(new Point(2,3),new Point(-4,0));
			MyLine l2=new MyLine(new Point(-3,1),new Point(-1,2));
			Point p1=new Point(0,0),p2=new Point(1,2);
			System.out.println("�߶�l1��"+l1);
			System.out.println("�߶�l2��"+l2);
			System.out.println("p1"+p1+" p2"+p2);
			System.out.println("�߶�l1�ĳ��ȣ�"+l1.lineLength());
			System.out.println("�߶�l2�ĳ��ȣ�"+l2.lineLength());
			System.out.println("�߶�l1�Ƿ��ڵ�һ���ޣ�"+l1.check());
			System.out.println("�߶�l2�Ƿ��ڵ�һ���ޣ�"+l2.check());
			System.out.println("l1��l2�Ƿ��ཻ��"+l1.intersect(l2));
			System.out.println("l1��l2����ֱ�ߴ�ֱ���룺"+l1.lineDistance(l2));
			System.out.println("p1��l1�ľ��룺"+l1.pointDistance(p1));
			System.out.println("p1��l2�ľ��룺"+l2.pointDistance(p1));
			System.out.println("p2��l1�ľ��룺"+l1.pointDistance(p2));
			System.out.println("p2��l2�ľ��룺"+l2.pointDistance(p2));
			
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}

}

/** ����
 * @author user
 */
class Point {
	double x,y;
	public Point(double x0,double y0) {
		x=x0;
		y=y0;
	}
	public Point() {
		x=0.0;
		y=0.0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj!=null&&this.getClass()==obj.getClass()) {
			Point p=(Point)obj;
			if(x==p.x&&y==p.y) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
