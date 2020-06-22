
public class CommandLine {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		if(args.length==0) {
			System.out.println(System.getProperties());
		}
		else {
			for(int i=0;i<args.length;i++) {
				System.out.println(System.getProperty(args[i]));
			}
		}
	}

}
