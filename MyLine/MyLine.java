/** 线段类
 * @author user
 */
public class MyLine {
	
	/** 线段的两个端点 */
	Point e1,e2;
	
	/** 构造方法
	 * @param p1 线段的其中一个端点
	 * @param p2 线段的其中一个端点
	 * @throws Exception 如果两个端点相等（即在同一个位置），则抛出异常
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
	
	/** 求线段所在直线的方程a*x+b*y+c=0的每项系数，并依次保存在数组中 */
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
	
	/** 检查线段是否位于第一象限
	 * @return true:线段位于第一象限，false反之
	 */
	public boolean check() {
		if(e1.x>0&&e1.y>0&&e2.x>0&&e2.y>0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/** 求线段的长度 
	 * @return 线段的长度
	 */
	public double lineLength() {
		double sqr=(e2.x-e1.x)*(e2.x-e1.x)+(e2.y-e1.y)*(e2.y-e1.y);
		return Math.sqrt(sqr);
	}
	
	/** 判断两条线段所在直线是否相交
	 * @param l1 另一条线段
	 * @return true：两直线相交，false反之
	 */
	public boolean intersect(MyLine l1) {
		double[] r1=equation();
		double[] r2=l1.equation();
		if(r1[0]*r2[1]-r2[0]*r1[1]!=0) { // 相交
			return true;
		}
		else { // 平行或重合
			return false;
		}
	}
	
	/** 求一点到该线段（或延长线）的距离 
	 * @param p 给定的点
	 * @return 该点到该线段（或延长线）的距离
	 */
	public double pointDistance(Point p) {
		double[] r=equation();
		double d=(r[0]*p.x+r[1]*p.y+r[2])/Math.sqrt(r[0]*r[0]+r[1]*r[1]);
		return Math.abs(d);
	}
	
	/** 求两条线段所在直线的垂直距离
	 * @param l1 另一条线段
	 * @return 两条线段所在直线的垂直距离，若相交或重合结果为0.0
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
		// TODO 自动生成的方法存根
		try {
			MyLine l1=new MyLine(new Point(2,3),new Point(-4,0));
			MyLine l2=new MyLine(new Point(-3,1),new Point(-1,2));
			Point p1=new Point(0,0),p2=new Point(1,2);
			System.out.println("线段l1："+l1);
			System.out.println("线段l2："+l2);
			System.out.println("p1"+p1+" p2"+p2);
			System.out.println("线段l1的长度："+l1.lineLength());
			System.out.println("线段l2的长度："+l2.lineLength());
			System.out.println("线段l1是否在第一象限："+l1.check());
			System.out.println("线段l2是否在第一象限："+l2.check());
			System.out.println("l1与l2是否相交："+l1.intersect(l2));
			System.out.println("l1与l2所在直线垂直距离："+l1.lineDistance(l2));
			System.out.println("p1到l1的距离："+l1.pointDistance(p1));
			System.out.println("p1到l2的距离："+l2.pointDistance(p1));
			System.out.println("p2到l1的距离："+l1.pointDistance(p2));
			System.out.println("p2到l2的距离："+l2.pointDistance(p2));
			
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}

}

/** 点类
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
