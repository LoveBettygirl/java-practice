
public class CommandLine {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
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
