import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;

/**
 * 文本替换
 * @author user
 */
public class TextReplace extends JFrame {
	
	private JLabel label1=new JLabel("选择txt文件：");
	private JLabel label2=new JLabel("旧字符串：");
	private JLabel label3=new JLabel("新字符串：");
	private JLabel label4=new JLabel("原来的文件：");
	private JLabel label5=new JLabel("替换后的文件：");
	
	private TextFieldMenu filePath=new TextFieldMenu(50);
	private TextFieldMenu oldText=new TextFieldMenu(50);
	private TextFieldMenu newText=new TextFieldMenu(50);
	private TextAreaMenu oldFile=new TextAreaMenu(10,20);
	private TextAreaMenu newFile=new TextAreaMenu(10,20);
	
	private JButton button1=new JButton("选择文件");
	private JButton button2=new JButton("开始替换");
	private JButton button3=new JButton("Console");
	private JButton button4=new JButton("Console");
	private JButton button5=new JButton("帮助");
	
	/** 要打开的文件 */
	private File file=null;
	
	private Scanner sc=new Scanner(System.in);
	
	/**
	 * 使用帮助
	 * @author user
	 */
	private class Helper extends JDialog {
		public Helper() {
			super(TextReplace.this,"Helper");
			setSize(300,200);
			TextAreaMenu text=new TextAreaMenu(20,30);
			text.setEditable(false);
			text.append("只能替换txt文件中的文本。\n");
			text.append("文本框支持复制粘贴。\n");
			text.append("支持查看txt文档。\n");
			text.setCaretPosition(0);//将光标置于最前
			add(new JScrollPane(text));
		}
	}
	
	private class TextFieldMenu extends JTextField implements MouseListener {
		
		private JPopupMenu pop=new JPopupMenu(); 

		private JMenuItem copy=new JMenuItem("复制(C)");
		private JMenuItem paste=new JMenuItem("粘贴(P)");
		private JMenuItem cut=new JMenuItem("剪切(t)");
		
		public TextFieldMenu(int columns) {
			super(columns);
		    initialUI();
		}
		
		public TextFieldMenu() {
			super();
		    initialUI();
		}

		private void initialUI() {
			addMouseListener(this);
			
		    pop.add(copy);
		    pop.add(paste);
		    pop.add(cut);
		    
		    //添加快捷键
		    copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
		    paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
		    cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
		    
		    copy.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		action(e);
		    	}
		    });
		    
		    paste.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		action(e);
		    	}
		    });
		    
		    cut.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		action(e);
		    	}
		    });
		    
		    add(pop);
		   }

		   /**
		    * 菜单动作
		    * @param e
		    */
		   public void action(ActionEvent e) {
			   String str=e.getActionCommand();
			   if (str.equals(copy.getText())) { //复制
				   copy();
			   } 
			   else if (str.equals(paste.getText())) { //粘贴
				   paste();
			   } 
			   else if (str.equals(cut.getText())) { //剪切
				   cut();
			   }
		   }

		   public JPopupMenu getPop() {
			   return pop;
		   }

		   public void setPop(JPopupMenu pop) {
			   this.pop = pop;
		   }

		   /**
		    * 剪切板中是否有文本数据可供粘贴
		    * @return true为有文本数据
		    */
		   public boolean isClipboardString() {
			   boolean b=false;
			   Clipboard clipboard=this.getToolkit().getSystemClipboard();
			   Transferable content=clipboard.getContents(this);
			   try {
				   if (content.getTransferData(DataFlavor.stringFlavor) instanceof String) {
					   b=true;
				   }
			   } 
			   catch (Exception e) {}
			   return b;
		   }

		   /**
		    * 文本组件中是否具备复制的条件
		    * @return true为具备
		    */
		   public boolean isCanCopy() {
			   boolean b=false;
			   int start=this.getSelectionStart();
			   int end=this.getSelectionEnd();
			   if (start!=end)
				   b=true;
			   return b;
		   }

		   public void mouseClicked(MouseEvent e) {}

		   public void mouseEntered(MouseEvent e) {}

		   public void mouseExited(MouseEvent e) {}

		   public void mousePressed(MouseEvent e) {
			   if (e.getButton()==MouseEvent.BUTTON3) {
				   copy.setEnabled(isCanCopy());
				   paste.setEnabled(isClipboardString());
				   cut.setEnabled(isCanCopy());
				   pop.show(this, e.getX(), e.getY());
			   }
		   }

		   public void mouseReleased(MouseEvent e) {}

	}
	
	private class TextAreaMenu extends JTextArea implements MouseListener {
		
		private JPopupMenu pop=new JPopupMenu(); 

		private JMenuItem copy=new JMenuItem("复制(C)");
		private JMenuItem paste=new JMenuItem("粘贴(P)");
		private JMenuItem cut=new JMenuItem("剪切(t)");
		
		public TextAreaMenu(int rows,int columns) {
			super(rows,columns);
		    initialUI();
		}
		
		public TextAreaMenu() {
			super();
		    initialUI();
		}

		private void initialUI() {
			addMouseListener(this);
			
		    pop.add(copy);
		    pop.add(paste);
		    pop.add(cut);
		    
		    //添加快捷键
		    copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
		    paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
		    cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
		    
		    copy.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		action(e);
		    	}
		    });
		    
		    paste.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		action(e);
		    	}
		    });
		    
		    cut.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		action(e);
		    	}
		    });
		    
		    add(pop);
		 }

		   /**
		    * 菜单动作
		    * @param e
		    */
		   public void action(ActionEvent e) {
			   String str=e.getActionCommand();
			   if (str.equals(copy.getText())) { //复制
				   copy();
			   } 
			   else if (str.equals(paste.getText())) { //粘贴
				   paste();
			   } 
			   else if (str.equals(cut.getText())) { //剪切
				   cut();
			   }
		   }

		   public JPopupMenu getPop() {
			   return pop;
		   }

		   public void setPop(JPopupMenu pop) {
			   this.pop=pop;
		   }

		   /**
		    * 剪切板中是否有文本数据可供粘贴
		    * @return true为有文本数据
		    */
		   public boolean isClipboardString() {
			   boolean b=false;
			   Clipboard clipboard=this.getToolkit().getSystemClipboard();
			   Transferable content=clipboard.getContents(this);
			   try {
				   if (content.getTransferData(DataFlavor.stringFlavor) instanceof String) {
					   b=true;
				   }
			   } 
			   catch (Exception e) {}
			   return b;
		   }

		   /**
		    * 文本组件中是否具备复制的条件
		    * @return true为具备
		    */
		   public boolean isCanCopy() {
			   boolean b=false;
			   int start=this.getSelectionStart();
			   int end=this.getSelectionEnd();
			   if (start!=end)
				   b=true;
			   return b;
		   }

		   public void mouseClicked(MouseEvent e) {}

		   public void mouseEntered(MouseEvent e) {}

		   public void mouseExited(MouseEvent e) {}

		   public void mousePressed(MouseEvent e) {
			   if (e.getButton()==MouseEvent.BUTTON3) {
				   copy.setEnabled(isCanCopy());
				   paste.setEnabled(isClipboardString());
				   cut.setEnabled(isCanCopy());
				   pop.show(this, e.getX(), e.getY());
			   }
		   }

		   public void mouseReleased(MouseEvent e) {}

	}
	
	/**
	 * 初始化界面
	 */
	public void initialUI() {
		
		setResizable(false);//设置窗体不能缩放
		setLayout(null);
		
		add(label1);
		label1.setBounds(40,15,100,50);
		add(filePath);
		filePath.setBounds(140,27,450,25);
		add(button1);
		button1.setBounds(610,27,100,25);
		
		add(label2);
		label2.setBounds(40,55,100,50);
		add(oldText);
		oldText.setBounds(140,67,450,25);
		add(button3);
		button3.setBounds(610,67,100,25);
		button3.setEnabled(false);
		
		add(label3);
		label3.setBounds(40,95,100,50);
		add(newText);
		newText.setBounds(140,107,450,25);
		add(button4);
		button4.setBounds(610,107,100,25);
		button4.setEnabled(false);
		
		add(label4);
		label4.setBounds(40,135,150,50);
		JScrollPane p1=new JScrollPane(oldFile);
		p1.setBounds(40,180,300,250);
		oldFile.setEditable(false);
		add(p1);
		p1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        p1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		add(label5);
		label5.setBounds(400,135,150,50);
		JScrollPane p2=new JScrollPane(newFile);
		p2.setBounds(400,180,300,250);
		newFile.setEditable(false);
		add(p2);
		p2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        p2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		add(button2);
		button2.setBounds(240,450,100,25);
		add(button5);
		button5.setBounds(400,450,100,25);
	}
	
	/**
	 * 为每个组件注册监听器
	 */
	public void makeListener() {
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser chooser=new JFileChooser();
			        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			        chooser.showDialog(new JLabel(), "选择");
			        file=chooser.getSelectedFile();
			        if(file.isDirectory()){
			            System.out.println("path:"+file.getAbsolutePath());
			        }else if(file.isFile()){
			            System.out.println("file:"+file.getAbsolutePath());
			        }
			        System.out.println(chooser.getSelectedFile());
			        filePath.setText(file.toString());
			        if(!file.exists()) {
			        	oldFile.setText("");
			        	newFile.setText("");
						JOptionPane.showMessageDialog(null, "文件不存在！", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else if(file.isDirectory()){
						oldFile.setText("");
						newFile.setText("");
						JOptionPane.showMessageDialog(null, "文件格式不是txt！", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else {
						String s1=filePath.getText();
						if(!s1.contains(".")) {
							oldFile.setText("");
							newFile.setText("");
							JOptionPane.showMessageDialog(null, "文件格式不是txt！", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						else {
							s1=s1.substring(s1.indexOf(".")+1);
						}
						if(!(s1.equals("txt")||s1.equals("TXT")||s1.equals("Txt")||s1.equals("tXt")
							||s1.equals("txT")||s1.equals("tXT")||s1.equals("TxT")||s1.equals("TXt"))) {
							oldFile.setText("");
							newFile.setText("");
							JOptionPane.showMessageDialog(null, "文件格式不是txt！", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						FileReader fr=new FileReader(file.toString());
						BufferedReader br= new BufferedReader(fr);
						String s;
						StringBuffer buffer=new StringBuffer();
						int line=0;
						while((s=br.readLine())!=null) {
							if(line>0)
								buffer.append("\n");
							buffer.append(s);
							line++;
						}
						br.close();
						fr.close();
						s=buffer.toString();
						oldFile.setText(s);
					}
				}
				catch(Exception ee) {}
			}
		});
		
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "在控制台输入字符串！", "Message", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("Input a string for oldText:");
				String s=sc.next();
				oldText.setText(s);
			}
		});
		
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "在控制台输入字符串！", "Message", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("Input a string for newText:");
				String s=sc.next();
				newText.setText(s);
			}
		});
		
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(filePath.getText().equals("")) {
					newFile.setText("");
					oldFile.setText("");
					JOptionPane.showMessageDialog(null, "文件名不能为空！", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else {
					file=new File(filePath.getText());
					if(!file.exists()) {
						oldFile.setText("");
						newFile.setText("");
						JOptionPane.showMessageDialog(null, "文件不存在！", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else if(file.isDirectory()){
						oldFile.setText("");
						newFile.setText("");
						JOptionPane.showMessageDialog(null, "文件格式不是txt！", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else {
						String s1=filePath.getText();
						if(!s1.contains(".")) {
							oldFile.setText("");
							newFile.setText("");
							JOptionPane.showMessageDialog(null, "文件格式不是txt！", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						else {
							s1=s1.substring(s1.indexOf(".")+1);
						}
						if(!(s1.equals("txt")||s1.equals("TXT")||s1.equals("Txt")||s1.equals("tXt")
							||s1.equals("txT")||s1.equals("tXT")||s1.equals("TxT")||s1.equals("TXt"))) {
							oldFile.setText("");
							newFile.setText("");
							JOptionPane.showMessageDialog(null, "文件格式不是txt！", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						if(oldText.getText().equals("")) {
							newFile.setText("");
							JOptionPane.showMessageDialog(null, "旧字符串不能为空！", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						else if(newText.getText().equals("")) {
							newFile.setText("");
							JOptionPane.showMessageDialog(null, "新字符串不能为空！", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						try {
							FileReader fr=new FileReader(file.toString());
							BufferedReader br= new BufferedReader(fr);
							String s;
							StringBuffer buffer=new StringBuffer();
							int line=0;
							while((s=br.readLine())!=null) {
								if(line>0)
									buffer.append("\n");
								buffer.append(s);
								line++;
							}
							br.close();
							fr.close();
							s=buffer.toString();
							oldFile.setText(s);
							s=oldFile.getText();
							LinkedList<String> result=new LinkedList<String>();
							String oldstr=oldText.getText();
							if(!s.contains(oldstr)) {
								newFile.setText("");
								JOptionPane.showMessageDialog(null, "该txt文件不存在此旧字符串！", "Error", JOptionPane.ERROR_MESSAGE);
								return;
							}
							int start=s.indexOf(oldstr);
							int end=start+oldstr.length()-1;
							String sub1=s.substring(0,start);
							String sub2=s.substring(end+1,s.length());
							String newstr=sub1+newText.getText()+sub2;
							newFile.setText(newstr);
							int last=0,post=0;
							for(int i=0;i<newstr.length();i++) {
								post=i;
								if(newstr.charAt(i)=='\n'||i==newstr.length()-1) {
									String temp;
									if(i==newstr.length()-1) {
										temp=newstr.substring(last,i+1);
									}
									else {
										temp=newstr.substring(last,post);
									}
									result.add(temp);
									last=i+1;
								}
							}
							PrintWriter pw=new PrintWriter(file.toString());
							BufferedWriter bw=new BufferedWriter(pw);
							Iterator<String> i=result.iterator();
							while(i.hasNext()) {
								bw.write(i.next());//BufferedWriter属于过滤流，write方法输出到文件会过滤掉换行符
								if(i.hasNext()) {
									bw.newLine();//故一行一行输出
								}
							}
							bw.flush();
							bw.close();
							pw.flush();
							pw.close();
						}
						catch(Exception ee) {
							ee.printStackTrace();
						}
					}
				}
			}
		});
		
		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Helper().setVisible(true);
			}
		});
		
		filePath.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()!=KeyEvent.VK_ENTER)
					return;
				if(filePath.getText().equals("")) {
					newFile.setText("");
					oldFile.setText("");
					JOptionPane.showMessageDialog(null, "文件名不能为空！", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				file=new File(filePath.getText());
				if(!file.exists()) {
					oldFile.setText("");
					JOptionPane.showMessageDialog(null, "文件不存在！", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else if(file.isDirectory()){
					oldFile.setText("");
					JOptionPane.showMessageDialog(null, "文件格式不是txt！", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else {
					String s1=filePath.getText();
					if(!s1.contains(".")) {
						oldFile.setText("");
						JOptionPane.showMessageDialog(null, "文件格式不是txt！", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else {
						s1=s1.substring(s1.indexOf(".")+1);
					}
					if(!(s1.equals("txt")||s1.equals("TXT")||s1.equals("Txt")||s1.equals("tXt")
						||s1.equals("txT")||s1.equals("tXT")||s1.equals("TxT")||s1.equals("TXt"))) {
						oldFile.setText("");
						JOptionPane.showMessageDialog(null, "文件格式不是txt！", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					try {
						FileReader fr=new FileReader(file.toString());
						BufferedReader br= new BufferedReader(fr);
						String s;
						StringBuffer buffer=new StringBuffer();
						int line=0;
						while((s=br.readLine())!=null) {
							if(line>0)
								buffer.append("\n");
							buffer.append(s);
							line++;
						}
						br.close();
						fr.close();
						s=buffer.toString();
						oldFile.setText(s);
					}
					catch(Exception ee) {}
				}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
	}
	
	public TextReplace() {
		initialUI();
		makeListener();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Console.run(new TextReplace(), 800, 600);
	}

}
