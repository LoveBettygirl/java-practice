import java.util.*;
import javax.swing.*;
import javax.swing.plaf.metal.*;
import java.math.*;
import javax.swing.plaf.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame {
	
	/** 界面布局分割 */
	private JPanel panel1=new JPanel(),panel2=new JPanel(),panel3=new JPanel(),panel4=new JPanel();
	
	/** 菜单栏 */
	private JMenuBar menuBar=new JMenuBar();
	private JMenu menu1=new JMenu("选项(O)");
	private JMenu item1=new JMenu("三角函数设置");
	private JMenu item2=new JMenu("外观设置");
	private JMenuItem item3=new JMenuItem("帮助");
	private JRadioButtonMenuItem item4=new JRadioButtonMenuItem("角度制");
	private JRadioButtonMenuItem item5=new JRadioButtonMenuItem("弧度制");
	private JRadioButtonMenuItem item6=new JRadioButtonMenuItem("金属外观（默认）");
	private JRadioButtonMenuItem item7=new JRadioButtonMenuItem("系统外观");
	private ButtonGroup menuGroup1=new ButtonGroup();
	private ButtonGroup menuGroup2=new ButtonGroup();
	
	/** 文本框 */
	private JTextField memory=new JTextField(40);
	private JTextField result=new JTextField(40);
	private JTextField message=new JTextField(1000);
	
	/** 字体 */
	private Font memoryFont=new Font("楷体",0,40);
	private Font resultFont=new Font("楷体",0,40);
	private Font meassageFont=new Font("Times New Roman",0,25);
	private Font buttonFont=new Font("Arial",0,20);
	
	private double memorynum=0;
	private double resultnum=0;
	private double opnum1=0,opnum2=0;
	
	private boolean useMemory=false;
	private boolean isFinalResult=true;//已经得出最后结果为true
	private boolean inputing=true;
	private boolean error=false;
	private boolean degree=true;
	private boolean metalUI=true;
	
	private Stack<String> numStack=new Stack<String>();
	private Stack<String> opeStack=new Stack<String>();
	
	private String substrpre="";
	private String substrpost="0";
	
	private JButton[] button= {
		new JButton("sin"),new JButton("MC"),new JButton("MR"),new JButton("M+"),new JButton("M-"),
		new JButton("MS"),new JButton("cos"),new JButton("%"),new JButton("CE"),new JButton("C"),
		new JButton("←"),new JButton("÷"),new JButton("tan"),new JButton("√"),new JButton("7"),
		new JButton("8"),new JButton("9"),new JButton("×"),new JButton("π"),new JButton("x^2"),
		new JButton("4"),new JButton("5"),new JButton("6"),new JButton("-"),new JButton("x^y"),
		new JButton("1/x"),new JButton("1"),new JButton("2"),new JButton("3"),new JButton("+"),
		new JButton("e^x"),new JButton("ln"),new JButton("±"),new JButton("0"),new JButton("."),
		new JButton("=") 
	};
	
	/**
	 * 设置错误状态
	 */
	private void setErrorStatus() {
		button[0].setEnabled(false);
		button[1].setEnabled(false);
		button[2].setEnabled(false);
		button[3].setEnabled(false);
		button[4].setEnabled(false);
		button[5].setEnabled(false);
		button[6].setEnabled(false);
		button[7].setEnabled(false);
		button[11].setEnabled(false);
		button[12].setEnabled(false);
		button[13].setEnabled(false);
		button[17].setEnabled(false);
		button[18].setEnabled(false);
		button[19].setEnabled(false);
		button[23].setEnabled(false);
		button[24].setEnabled(false);
		button[25].setEnabled(false);
		button[29].setEnabled(false);
		button[17].setEnabled(false);
		button[30].setEnabled(false);
		button[31].setEnabled(false);
		button[32].setEnabled(false);
		button[34].setEnabled(false);
	}
	
	/**
	 * 设置正常状态
	 */
	private void setNormalStatus() {
		for(int i=0;i<button.length;i++) {
			if(i==1||i==2) {
				if(useMemory) {
					button[i].setEnabled(true);
				}
				else {
					button[i].setEnabled(false);
				}
			}
			else {
				button[i].setEnabled(true);
			}
		}
	}
	
	/**
	 * 初始化界面
	 */
	public void initialUI() {
		
		menuBar.add(menu1);
		menu1.add(item1);
		menu1.add(item2);
		menu1.add(item3);
		item1.add(item4);
		item1.add(item5);
		item2.add(item6);
		item2.add(item7);
		menuGroup1.add(item4);
		menuGroup1.add(item5);
		menuGroup2.add(item6);
		menuGroup2.add(item7);
		
		
		if(degree) {
			item4.setSelected(true);
		}
		else {
			item4.setSelected(false);
		}
		
		if(metalUI) {
			item6.setSelected(true);
		}
		else {
			item6.setSelected(false);
		}
		
		setJMenuBar(menuBar);
		
		setResizable(false);//设置窗体不能缩放
		
		memory.setEditable(false);
		result.setEditable(false);
		message.setEditable(false);
		
		setLayout(new GridLayout(4,1,6,6));
		panel1.setLayout(null);
		panel2.setLayout(new GridLayout(2,6,6,6));
		panel3.setLayout(new GridLayout(2,6,6,6));
		panel4.setLayout(new GridLayout(2,6,6,6));
		
		panel1.add(memory);
		memory.setBounds(20,40,100,50);//设置大小及位置
		memory.setFont(memoryFont);//设置字体
		memory.setText("0");
		memory.setBorder(null);//设置无边界
		memory.setBackground(Color.white);//设置背景色
		memory.setHorizontalAlignment(JTextField.RIGHT);//文本右对齐
		
		panel1.add(message);
		message.setBounds(150,15,610,50);
		message.setFont(meassageFont);
		message.setText("");
		message.setBorder(null);
		message.setBackground(Color.white);
		message.setHorizontalAlignment(JTextField.RIGHT);
		
		panel1.add(result);
		result.setBounds(150,65,610,50);
		result.setFont(resultFont);
		result.setText("0");
		result.setBorder(null);
		result.setBackground(Color.white);
		result.setHorizontalAlignment(JTextField.RIGHT);
		
		int i;
		for(i=0;i<12;i++) {
			panel2.add(button[i]);
			button[i].setFont(buttonFont);
		}
		for(i=12;i<24;i++) {
			panel3.add(button[i]);
			button[i].setFont(buttonFont);
		}
		for(i=24;i<36;i++) {
			panel4.add(button[i]);
			button[i].setFont(buttonFont);
		}
		add(panel1);
		add(panel2);
		add(panel3);
		add(panel4);
		
		if(useMemory) {
			button[1].setEnabled(true);
			button[2].setEnabled(true);
		}
		else {
			button[1].setEnabled(false);
			button[2].setEnabled(false);
		}
		
	}
	
	/**
	 * 使用帮助
	 * @author user
	 */
	private class Helper extends JDialog {
		public Helper() {
			super(Calculator.this,"Helper");
			setSize(300,200);
			JTextArea text=new JTextArea(20,30);
			text.setEditable(false);
			text.append("本计算器考虑运算符优先级，但不允许自行加括号。\n");
			text.append("三角函数计算默认采用角度制，也可以选择弧度制计算。\n");
			text.append("外观可选择默认金属外观和系统外观。\n");
			text.append("左边小方格为内存，右边大方格为算式和当前数据。");
			text.append("MS：将当前数据写入内存。\n");
			text.append("M+：将当前数据与内存中的数据相加后再存入内存。\n");
			text.append("M-：将当前数据与内存中的数据相减后再存入内存。\n");
			text.append("MR：读出内存中的数据到当前数据。\n");
			text.append("MC：清除内存中的数据，使其为0。\n");
			text.append("%：当操作数不只一个时，将当前数据除以100；否则令当前数据为0。\n");
			text.append("C：清除算式，并将当前数据置为0。\n");
			text.append("CE：将当前数据置为0，不清除算式。\n");
			text.append("←：输入状态下使当前数据退格。\n");
			text.setCaretPosition(0);//将光标置于最前
			add(new JScrollPane(text));
		}
	}
	
	/** 
	 * 数字按键监听器
	 * @author user
	 */
	private class NumListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!inputing) {
				result.setText("");
			}
			if(isFinalResult||error) {
				result.setText("");
				message.setText("");
				substrpre="";
				numStack.clear();
				opeStack.clear();
				setNormalStatus();
			}
			isFinalResult=false;
			inputing=true;
			error=false;
			String name=((JButton)e.getSource()).getText();
			if(result.getText().equals("0")||isFinalResult) {
				result.setText(name);
				isFinalResult=false;
			}
			else if(result.getText().length()<29||result.getText().endsWith(".")) {
				result.setText(result.getText()+name);
			}
			//numStack.add(result.getText());
			//System.out.println(numStack.peek()+" has entered the numStack.");
			resultnum=Double.parseDouble(result.getText());
			substrpost=result.getText();
		}
	}
	
	/**
	 * 操作符监听器
	 * @author user
	 */
	private class OpeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String ope=((JButton)e.getSource()).getText();
			String m=message.getText();
			isFinalResult=false;
			if(m.contains("=")) {
				message.setText("");
				substrpre="";
				substrpost=result.getText();
				numStack.clear();
				opeStack.clear();
			}
			if(result.getText().endsWith(".")) {
				String t=result.getText();
				t=t.substring(0, t.length()-1);
				result.setText(t);
			}
			if(ope.equals("+")||ope.equals("-")||ope.equals("×")||ope.equals("÷")||ope.equals("x^y")) {
				if(ope.equals("x^y")) {
					ope="^";
				}
				if(!inputing&&!isFinalResult) {//改变运算符
					inputing=false;
					if(ope.equals("+")||ope.equals("-")) {
						String pops=opeStack.pop();
						System.out.println(pops+" pop from opeStack");
						opeStack.push(ope);
						System.out.println(ope+" push into opeStack");
						m=m.substring(0, m.length()-1);
						m=m+ope;
						message.setText(m);
						substrpre=m;
						return;
					}
					else if(ope.equals("×")||ope.equals("÷")) {
						String mm=message.getText();
						mm=mm.substring(0, mm.length()-1);
						if(mm.contains("+")||mm.contains("-")) {
							String pops=opeStack.pop();
							System.out.println(pops+" pop from opeStack");
							opeStack.push(ope);
							System.out.println(ope+" push into opeStack");
							m=m.substring(0, m.length()-1);
							m="("+m+")"+ope;
							message.setText(m);
							substrpre=m;
							return;
						}
						else {
							String pops=opeStack.pop();
							System.out.println(pops+" pop from opeStack");
							opeStack.push(ope);
							System.out.println(ope+" push into opeStack");
							m=m.substring(0, m.length()-1);
							m=m+ope;
							message.setText(m);
							substrpre=m;
							return;
						}
					}
					else if(ope.equals("^")) {
						String mm=message.getText();
						mm=mm.substring(0, mm.length()-1);
						if(mm.contains("+")||mm.contains("-")||mm.contains("×")||mm.contains("÷")) {
							String pops=opeStack.pop();
							System.out.println(pops+" pop from opeStack");
							opeStack.push(ope);
							System.out.println(ope+" push into opeStack");
							m=m.substring(0, m.length()-1);
							m="("+m+")"+ope;
							message.setText(m);
							substrpre=m;
							return;
						}
						else {
							String pops=opeStack.pop();
							System.out.println(pops+" pop from opeStack");
							opeStack.push(ope);
							System.out.println(ope+" push into opeStack");
							m=m.substring(0, m.length()-1);
							m=m+ope;
							message.setText(m);
							substrpre=m;
							return;
						}
					}
				}
				else {
					inputing=false;
					if(ope.equals("+")||ope.equals("-")) {
						if(message.getText().endsWith(")")) {
							message.setText(message.getText()+ope);
						}
						else {
							message.setText(message.getText()+result.getText()+ope);
						}
						numStack.push(result.getText());
						System.out.println(result.getText()+" push into numStack");
						while(!opeStack.isEmpty()) {
							String numt=numStack.peek(),opet=opeStack.pop();
							System.out.println(opet+" pop from opeStack");
							String num2=numStack.pop();
							System.out.println(num2+" pop from numStack");
							String num1=numStack.pop();
							System.out.println(num1+" pop from numStack");
							if(opet.equals("+")) {
								resultnum=Double.parseDouble(num1)+Double.parseDouble(num2);
								int numi=(int)resultnum;
								double sub=resultnum-numi;
								if(sub==0) {
									numStack.push(Integer.toString(numi));
								}
								else {
									numStack.push(Double.toString(resultnum));
								}
								System.out.println(numStack.peek()+" push into numStack");
							}
							else if(opet.equals("-")) {
								resultnum=Double.parseDouble(num1)-Double.parseDouble(num2);
								int numi=(int)resultnum;
								double sub=resultnum-numi;
								if(sub==0) {
									numStack.push(Integer.toString(numi));
								}
								else {
									numStack.push(Double.toString(resultnum));
								}
								System.out.println(numStack.peek()+" push into numStack");
							}
							else if(opet.equals("×")) {
								resultnum=Double.parseDouble(num1)*Double.parseDouble(num2);
								int numi=(int)resultnum;
								double sub=resultnum-numi;
								if(sub==0) {
									numStack.push(Integer.toString(numi));
								}
								else {
									numStack.push(Double.toString(resultnum));
								}
								System.out.println(numStack.peek()+" push into numStack");
							}
							else if(opet.equals("÷")) {
								if(Double.parseDouble(num2)==0) {
									if(Double.parseDouble(num1)==0) {
										result.setText("结果未定义");
									}
									else {
										result.setText("除数不能为零");
									}
									isFinalResult=true;
									resultnum=0;
									substrpost="0";
									substrpre="";
									numStack.clear();
									opeStack.clear();
									error=true;
									setErrorStatus();
									return;
								}
								resultnum=Double.parseDouble(num1)/Double.parseDouble(num2);
								int numi=(int)resultnum;
								double sub=resultnum-numi;
								if(sub==0) {
									numStack.push(Integer.toString(numi));
								}
								else {
									numStack.push(Double.toString(resultnum));
								}
								System.out.println(numStack.peek()+" push into numStack");
							}
							else if(opet.equals("^")) {//规定x^y的优先级最高
								resultnum=Math.pow(Double.parseDouble(num1), Double.parseDouble(num2));
								if(Double.isNaN(resultnum)||Double.isInfinite(resultnum)) {
									result.setText("无效输入");
									isFinalResult=true;
									resultnum=0;
									substrpost="0";
									substrpre="";
									numStack.clear();
									opeStack.clear();
									error=true;
									setErrorStatus();
									return;
								}
								int numi=(int)resultnum;
								double sub=resultnum-numi;
								if(sub==0) {
									numStack.push(Integer.toString(numi));
								}
								else {
									numStack.push(Double.toString(resultnum));
								}
								System.out.println(numStack.peek()+" push into numStack");
							}
						}
						result.setText(numStack.peek());
						resultnum=Double.parseDouble(numStack.peek());
						substrpre=message.getText();
						opeStack.push(ope);
						System.out.println(ope+" push into opeStack");
					}
					else if(ope.equals("^")) {
						if(message.getText().endsWith(")")) {
							message.setText(message.getText()+ope);
						}
						else {
							message.setText(message.getText()+result.getText()+ope);
						}
						numStack.push(result.getText());
						System.out.println(result.getText()+" push into numStack");
						if(opeStack.isEmpty()) {
							opeStack.push(ope);
							System.out.println(ope+" push into opeStack");
							substrpre=message.getText();
							return;
						}
						boolean haslow=false;
						Iterator<String> i=opeStack.iterator();
						while(i.hasNext()) {
							String temp=i.next();
							if(temp.equals("+")||temp.equals("-")||temp.equals("×")||temp.equals("÷")) {//检查有没有优先级低的双目运算符
								haslow=true;
							}
						}
						if(haslow) {
							opeStack.push(ope);
							System.out.println(ope+" push into opeStack");
							substrpre=message.getText();
							return;
						}
						while(!opeStack.isEmpty()) {
							String numt=numStack.peek(),opet=opeStack.pop();
							System.out.println(opet+" pop from opeStack");
							String num2=numStack.pop();
							System.out.println(num2+" pop from numStack");
							String num1=numStack.pop();
							System.out.println(num1+" pop from numStack");
							if(opet.equals("^")) {
								resultnum=Math.pow(Double.parseDouble(num1), Double.parseDouble(num2));
								if(Double.isNaN(resultnum)||Double.isInfinite(resultnum)) {
									result.setText("无效输入");
									isFinalResult=true;
									resultnum=0;
									substrpost="0";
									substrpre="";
									numStack.clear();
									opeStack.clear();
									error=true;
									setErrorStatus();
									return;
								}
								int numi=(int)resultnum;
								double sub=resultnum-numi;
								if(sub==0) {
									numStack.push(Integer.toString(numi));
								}
								else {
									numStack.push(Double.toString(resultnum));
								}
								System.out.println(numStack.peek()+" push into numStack");
							}
						}
						result.setText(numStack.peek());
						resultnum=Double.parseDouble(numStack.peek());
						substrpre=message.getText();
						opeStack.push(ope);
						System.out.println(ope+" push into opeStack");
					}
					else if(ope.equals("×")||ope.equals("÷")||ope.equals("^")) {
						if(message.getText().endsWith(")")) {
							message.setText(message.getText()+ope);
						}
						else {
							message.setText(message.getText()+result.getText()+ope);
						}
						numStack.push(result.getText());
						System.out.println(result.getText()+" push into numStack");
						if(opeStack.isEmpty()) {
							opeStack.push(ope);
							System.out.println(ope+" push into opeStack");
							substrpre=message.getText();
							return;
						}
						boolean haslow=false;
						Iterator<String> i=opeStack.iterator();
						while(i.hasNext()) {
							String temp=i.next();
							if(temp.equals("+")||temp.equals("-")) {//检查有没有优先级低的双目运算符
								haslow=true;
							}
						}
						if(haslow) {
							opeStack.push(ope);
							System.out.println(ope+" push into opeStack");
							substrpre=message.getText();
							return;
						}
						while(!opeStack.isEmpty()) {
							String numt=numStack.peek(),opet=opeStack.pop();
							System.out.println(opet+" pop from opeStack");
							String num2=numStack.pop();
							System.out.println(num2+" pop from numStack");
							String num1=numStack.pop();
							System.out.println(num1+" pop from numStack");
							if(opet.equals("×")) {
								resultnum=Double.parseDouble(num1)*Double.parseDouble(num2);
								int numi=(int)resultnum;
								double sub=resultnum-numi;
								if(sub==0) {
									numStack.push(Integer.toString(numi));
								}
								else {
									numStack.push(Double.toString(resultnum));
								}
								System.out.println(numStack.peek()+" push into numStack");
							}
							else if(opet.equals("÷")) {
								if(Double.parseDouble(num2)==0) {
									if(Double.parseDouble(num1)==0) {
										result.setText("结果未定义");
									}
									else {
										result.setText("除数不能为零");
									}
									isFinalResult=true;
									resultnum=0;
									substrpost="0";
									substrpre="";
									numStack.clear();
									opeStack.clear();
									error=true;
									setErrorStatus();
									return;
								}
								resultnum=Double.parseDouble(num1)/Double.parseDouble(num2);
								int numi=(int)resultnum;
								double sub=resultnum-numi;
								if(sub==0) {
									numStack.push(Integer.toString(numi));
								}
								else {
									numStack.push(Double.toString(resultnum));
								}
								System.out.println(numStack.peek()+" push into numStack");
							}
							else if(opet.equals("^")) {
								resultnum=Math.pow(Double.parseDouble(num1), Double.parseDouble(num2));
								if(Double.isNaN(resultnum)||Double.isInfinite(resultnum)) {
									result.setText("无效输入");
									isFinalResult=true;
									resultnum=0;
									substrpost="0";
									substrpre="";
									numStack.clear();
									opeStack.clear();
									error=true;
									setErrorStatus();
									return;
								}
								//resultnum=Double.parseDouble("num1")-Double.parseDouble("num2");
								int numi=(int)resultnum;
								double sub=resultnum-numi;
								if(sub==0) {
									numStack.push(Integer.toString(numi));
								}
								else {
									numStack.push(Double.toString(resultnum));
								}
								System.out.println(numStack.peek()+" push into numStack");
							}
						}
						result.setText(numStack.peek());
						resultnum=Double.parseDouble(numStack.peek());
						substrpre=message.getText();
						opeStack.push(ope);
						System.out.println(ope+" push into opeStack");
					}
				}
			}
			else if(ope.equals("%")) {
				inputing=true;
				if(opeStack.isEmpty()) {
					substrpost="0";
					result.setText("0");
					resultnum=0;
				}
				else {
					resultnum=resultnum/100.0;
					int temp=(int)resultnum;
					if(resultnum-temp==0) {
						substrpost=Integer.toString(temp);
					}
					else {
						substrpost=Double.toString(resultnum);
					}
					result.setText(substrpost);
				}
			}
			else if(ope.equals("=")) {
				if(error==true) {
					message.setText("");
					result.setText("0");
					resultnum=0;
					error=false;
					setNormalStatus();
					return;
				}
				if(message.getText().endsWith(")")) {
					message.setText(message.getText()+ope);
				}
				else {
					message.setText(message.getText()+result.getText()+ope);
				}
				numStack.push(result.getText());
				System.out.println(result.getText()+" push into numStack");
				while(!opeStack.isEmpty()) {
					String numt=numStack.peek(),opet=opeStack.pop();
					System.out.println(opet+" pop from opeStack");
					String num2=numStack.pop();
					System.out.println(num2+" pop from numStack");
					String num1=numStack.pop();
					System.out.println(num1+" pop from numStack");
					if(opet.equals("+")) {
						resultnum=Double.parseDouble(num1)+Double.parseDouble(num2);
						int numi=(int)resultnum;
						double sub=resultnum-numi;
						if(sub==0) {
							numStack.push(Integer.toString(numi));
						}
						else {
							numStack.push(Double.toString(resultnum));
						}
						System.out.println(numStack.peek()+" push into numStack");
					}
					else if(opet.equals("-")) {
						resultnum=Double.parseDouble(num1)-Double.parseDouble(num2);
						int numi=(int)resultnum;
						double sub=resultnum-numi;
						if(sub==0) {
							numStack.push(Integer.toString(numi));
						}
						else {
							numStack.push(Double.toString(resultnum));
						}
						System.out.println(numStack.peek()+" push into numStack");
					}
					else if(opet.equals("×")) {
						resultnum=Double.parseDouble(num1)*Double.parseDouble(num2);
						int numi=(int)resultnum;
						double sub=resultnum-numi;
						if(sub==0) {
							numStack.push(Integer.toString(numi));
						}
						else {
							numStack.push(Double.toString(resultnum));
						}
						System.out.println(numStack.peek()+" push into numStack");
					}
					else if(opet.equals("÷")) {
						if(Double.parseDouble(num2)==0) {
							if(Double.parseDouble(num1)==0) {
								result.setText("结果未定义");
							}
							else {
								result.setText("除数不能为零");
							}
							isFinalResult=true;
							resultnum=0;
							substrpost="0";
							substrpre="";
							numStack.clear();
							opeStack.clear();
							error=true;
							setErrorStatus();
							return;
						}
						resultnum=Double.parseDouble(num1)/Double.parseDouble(num2);
						int numi=(int)resultnum;
						double sub=resultnum-numi;
						if(sub==0) {
							numStack.push(Integer.toString(numi));
						}
						else {
							numStack.push(Double.toString(resultnum));
						}
						System.out.println(numStack.peek()+" push into numStack");
					}
					else if(opet.equals("^")) {
						resultnum=Math.pow(Double.parseDouble(num1), Double.parseDouble(num2));
						if(Double.isNaN(resultnum)||Double.isInfinite(resultnum)) {
							result.setText("无效输入");
							isFinalResult=true;
							resultnum=0;
							substrpost="0";
							substrpre="";
							numStack.clear();
							opeStack.clear();
							error=true;
							setErrorStatus();
							return;
						}
						int numi=(int)resultnum;
						double sub=resultnum-numi;
						if(sub==0) {
							numStack.push(Integer.toString(numi));
						}
						else {
							numStack.push(Double.toString(resultnum));
						}
						System.out.println(numStack.peek()+" push into numStack");
					}
				}
				result.setText(numStack.peek());
				resultnum=Double.parseDouble(numStack.peek());
				substrpre=message.getText();
				opeStack.push(ope);
				System.out.println(ope+" push into opeStack");
				isFinalResult=true;
				return;
			}
			else {//单目
				inputing=true;
				numStack.push(result.getText());
				System.out.println(numStack.peek()+" push into numStack");
				//计算resultnum
				if(ope.equals("sin")) {
					double d=resultnum;
					if(degree) {
						d=Math.toRadians(resultnum);
					}
					resultnum=Math.sin(d);
					BigDecimal decimal=new BigDecimal(Double.toString(resultnum));
					decimal=decimal.setScale(15, RoundingMode.HALF_UP);
					substrpost="sin("+substrpost+")";
					m=substrpre+substrpost;
					message.setText(m);
					String st=numStack.pop();
					System.out.println(st+" pop from numStack");
					resultnum=decimal.doubleValue();
					double sub=resultnum-(int)resultnum;
					String newnum1;
					if(sub==0) {
						newnum1=Integer.toString((int)resultnum);
					}
					else {
						newnum1=Double.toString(resultnum);
					}
					result.setText(newnum1);
					isFinalResult=true;
					return;
				}
				else if(ope.equals("cos")) {
					double d=resultnum;
					if(degree) {
						d=Math.toRadians(resultnum);
					}
					resultnum=Math.cos(d);
					BigDecimal decimal=new BigDecimal(Double.toString(resultnum));
					decimal=decimal.setScale(15, RoundingMode.HALF_UP);
					substrpost="cos("+substrpost+")";
					m=substrpre+substrpost;
					message.setText(m);
					String st=numStack.pop();
					System.out.println(st+" pop from numStack");
					resultnum=decimal.doubleValue();
					double sub=resultnum-(int)resultnum;
					String newnum1;
					if(sub==0) {
						newnum1=Integer.toString((int)resultnum);
					}
					else {
						newnum1=Double.toString(resultnum);
					}
					result.setText(newnum1);
					isFinalResult=true;
					return;
				}
				else if(ope.equals("tan")) {
					double d=resultnum,g=resultnum;
					if(degree) {
						d=Math.toRadians(resultnum);
					}
					resultnum=Math.tan(d);
					substrpost="tan("+substrpost+")";
					BigDecimal decimal=new BigDecimal(Double.toString(resultnum));
					decimal=decimal.setScale(15, RoundingMode.HALF_UP);
					m=substrpre+substrpost;
					message.setText(m);
					String st=numStack.pop();
					System.out.println(st+" pop from numStack");
					resultnum=decimal.doubleValue();
					if(degree&&g%90==0&&(g/90)%2!=0) {
						result.setText("无效输入");
						isFinalResult=true;
						resultnum=0;
						substrpost="";
						substrpre="";
						numStack.clear();
						opeStack.clear();
						error=true;
						setErrorStatus();
						return;
					}
					double sub=resultnum-(int)resultnum;
					String newnum1;
					if(sub==0) {
						newnum1=Integer.toString((int)resultnum);
					}
					else {
						newnum1=Double.toString(resultnum);
					}
					result.setText(newnum1);
					isFinalResult=true;
					return;
				}
				else if(ope.equals("x^2")) {
					resultnum=resultnum*resultnum;
					substrpost="sqr("+substrpost+")";
					m=substrpre+substrpost;
					message.setText(m);
				}
				else if(ope.equals("e^x")) {
					resultnum=Math.exp(resultnum);
					substrpost="exp("+substrpost+")";
					m=substrpre+substrpost;
					message.setText(m);
				}
				else if(ope.equals("ln")) {
					double temp=Double.parseDouble(numStack.peek());
					substrpost="ln("+substrpost+")";
					m=substrpre+substrpost;
					message.setText(m);
					if(temp<=0) {
						result.setText("无效输入");
						isFinalResult=true;
						resultnum=0;
						substrpost="";
						substrpre="";
						numStack.clear();
						opeStack.clear();
						setErrorStatus();
						error=true;
						return;
					}
					resultnum=Math.log(resultnum);
				}
				else if(ope.equals("1/x")) {
					double temp=Double.parseDouble(numStack.peek());
					substrpost="reciproc("+substrpost+")";
					m=substrpre+substrpost;
					message.setText(m);
					if(temp==0) {
						result.setText("除数不能为零");
						isFinalResult=true;
						resultnum=0;
						substrpost="";
						substrpre="";
						numStack.clear();
						opeStack.clear();
						setErrorStatus();
						error=true;
						return;
					}
					resultnum=1/resultnum;
				}
				else if(ope.equals("√")) {
					double temp=Double.parseDouble(numStack.peek());
					substrpost="sqrt("+substrpost+")";
					m=substrpre+substrpost;
					message.setText(m);
					if(temp<0) {
						result.setText("无效输入");
						isFinalResult=true;
						resultnum=0;
						substrpost="";
						substrpre="";
						numStack.clear();
						opeStack.clear();
						setErrorStatus();
						error=true;
						return;
					}
					resultnum=Math.sqrt(resultnum);
				}
				String st=numStack.pop();
				System.out.println(st+" pop from numStack");
				double sub=resultnum-(int)resultnum;
				String newnum;
				if(sub==0) {
					newnum=Integer.toString((int)resultnum);
				}
				else {
					newnum=Double.toString(resultnum);
				}
				result.setText(newnum);
				isFinalResult=true;
				return;
			}
		}
	}
	
	/** 
	 * 为每个按键注册监听器
	 */
	public void makeListener() {
		button[0].addActionListener(new OpeListener());//sin
		
		button[1].addActionListener(new ActionListener() {//MC
			public void actionPerformed(ActionEvent e) {
				memory.setText("0");
				memorynum=0;
				useMemory=false;
				button[1].setEnabled(false);
				button[2].setEnabled(false);
			}
		});
		
		button[2].addActionListener(new ActionListener() {//MR
			public void actionPerformed(ActionEvent e) {
				result.setText(memory.getText());
				resultnum=Double.parseDouble(result.getText());
				substrpost=result.getText();
				isFinalResult=true;
				inputing=true;
			}
		});
		
		button[3].addActionListener(new ActionListener() {//M+
			public void actionPerformed(ActionEvent e) {
				String text=result.getText();
				memorynum+=Double.parseDouble(text);
				int temp=(int)memorynum;
				if(memorynum-temp==0) {
					memory.setText(Integer.toString(temp));
				}
				else {
					memory.setText(Double.toString(memorynum));
				}
				useMemory=true;
				button[1].setEnabled(true);
				button[2].setEnabled(true);
			}
		});
		
		button[4].addActionListener(new ActionListener() {//M-
			public void actionPerformed(ActionEvent e) {
				String text=result.getText();
				memorynum-=Double.parseDouble(text);
				int temp=(int)memorynum;
				if(memorynum-temp==0) {
					memory.setText(Integer.toString(temp));
				}
				else {
					memory.setText(Double.toString(memorynum));
				}
				useMemory=true;
				button[1].setEnabled(true);
				button[2].setEnabled(true);
			}
		});
		
		button[5].addActionListener(new ActionListener() {//MS
			public void actionPerformed(ActionEvent e) {
				String text=result.getText();
				memorynum=Double.parseDouble(text);
				memory.setText(text);
				useMemory=true;
				button[1].setEnabled(true);
				button[2].setEnabled(true);
			}
		});
		
		button[6].addActionListener(new OpeListener());//cos
		button[7].addActionListener(new OpeListener());//%
		
		button[8].addActionListener(new ActionListener() {//CE
			public void actionPerformed(ActionEvent e) {
				if(error) {
					setNormalStatus();
					message.setText("");
					opnum1=opnum2=resultnum=0;
					substrpre="";
					numStack.clear();
					opeStack.clear();
				}
				String m=message.getText();
				if(m.contains("=")) {
					message.setText("");
					substrpre="";
				}
				if(!m.contains("=")&&isFinalResult) {
					message.setText(substrpre);
				}
				result.setText("0");
				resultnum=0;
				substrpost="0";
				inputing=true;
			}
		});
		
		button[9].addActionListener(new ActionListener() {//C
			public void actionPerformed(ActionEvent e) {
				if(error) {
					setNormalStatus();
				}
				result.setText("0");
				message.setText("");
				opnum1=opnum2=resultnum=0;
				substrpre="";
				substrpost="0";
				numStack.clear();
				opeStack.clear();
				error=false;
				inputing=true;
			}
		});
		
		button[10].addActionListener(new ActionListener() {//←
			public void actionPerformed(ActionEvent e) {
				if(error) {
					setNormalStatus();
					result.setText("0");
					message.setText("");
					opnum1=opnum2=resultnum=0;
					substrpre="";
					substrpost="0";
					numStack.clear();
					opeStack.clear();
					error=false;
					inputing=true;
					return;
				}
				if(isFinalResult) {
					return;
				}
				String text=result.getText();
				if(text.length()==2&&text.charAt(0)=='-') {
					result.setText("0");
				}
				else if(text.length()>1) {
					result.setText(text.substring(0, text.length()-1));
				}
				else {
					result.setText("0");
				}
				resultnum=Double.parseDouble(result.getText());
				substrpost=result.getText();
			}
		});
		
		button[11].addActionListener(new OpeListener());//÷
		button[12].addActionListener(new OpeListener());//tan
		button[13].addActionListener(new OpeListener());//√
		
		button[14].addActionListener(new NumListener());//7
		button[15].addActionListener(new NumListener());//8
		button[16].addActionListener(new NumListener());//9
		
		button[17].addActionListener(new OpeListener());//×
		
		button[18].addActionListener(new ActionListener() {//π
			public void actionPerformed(ActionEvent e) {
				result.setText(Double.toString(Math.PI));
				substrpost=result.getText();
				opnum1=resultnum=Math.PI;
				isFinalResult=true;
				inputing=true;
			}
		});
		
		button[19].addActionListener(new OpeListener());//x^2
		
		button[20].addActionListener(new NumListener());//4
		button[21].addActionListener(new NumListener());//5
		button[22].addActionListener(new NumListener());//6
		
		button[23].addActionListener(new OpeListener());//-
		button[24].addActionListener(new OpeListener());//x^y
		button[25].addActionListener(new OpeListener());//1/x
		
		button[26].addActionListener(new NumListener());//1
		button[27].addActionListener(new NumListener());//2
		button[28].addActionListener(new NumListener());//3
		
		button[29].addActionListener(new OpeListener());//+
		button[30].addActionListener(new OpeListener());//e^x
		button[31].addActionListener(new OpeListener());//ln
		
		button[32].addActionListener(new ActionListener() {//±
			public void actionPerformed(ActionEvent e) {
				String text=result.getText();
				if(message.getText().contains("=")) {
					message.setText("");
					substrpre="";
					substrpost=result.getText();
					numStack.clear();
					opeStack.clear();
					inputing=false;
				}
				if(!result.getText().equals("0")) {//0
					if(text.charAt(0)!='-') {
						result.setText("-"+text);
						if(inputing&&!isFinalResult) {
							substrpost=result.getText();
						}
					}
					else {
						result.setText(text.substring(1, text.length()));
						if(inputing&&!isFinalResult) {
							substrpost=result.getText();
						}
					}
				}
				resultnum=-resultnum;
				if(!inputing||isFinalResult) {
					substrpost="negate("+substrpost+")";
					text=substrpre+substrpost;
					message.setText(text);
					isFinalResult=true;
				}
			}
		});
		
		button[33].addActionListener(new NumListener());//0
		
		button[34].addActionListener(new ActionListener() {//.
			public void actionPerformed(ActionEvent e) {
				String text=result.getText();
				if(message.getText().contains("=")) {
					message.setText("");
					substrpre="";
					substrpost="0";
					result.setText("0");
					numStack.clear();
					opeStack.clear();
					inputing=false;
				}
				inputing=true;
				if(isFinalResult) {
					result.setText("0.");
					resultnum=Double.parseDouble(result.getText());
					isFinalResult=false;
					return;
				}
				if(!text.contains(".")&&text.length()<29) {
					result.setText(text+".");
				}
				text=result.getText()+"0";
				resultnum=Double.parseDouble(text);
			}
		});
		button[35].addActionListener(new OpeListener());//=
		
		item3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Helper().setVisible(true);
			}
		});
		
		item4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(item5.isSelected()) {
					item5.setSelected(false);
				}
				degree=true;
			}
		});
		
		item5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(item4.isSelected()) {
					item4.setSelected(false);
				}
				degree=false;
			}
		});
		
		item6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(item7.isSelected()) {
					item7.setSelected(false);
				}
				metalUI=true;
				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				}
				catch(Exception ee) {
					ee.printStackTrace();
				}
				initialUI();
			}
		});
		
		item7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(item6.isSelected()) {
					item6.setSelected(false);
				}
				metalUI=false;
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					//UIManager.setLookAndFeel("com.sun.java."+"swing.plaf.motif.MotifLookAndFeel");
				}
				catch(Exception ee) {
					ee.printStackTrace();
				}
				initialUI();
			}
		});
	}
	
	public Calculator() {
		initialUI();
		makeListener();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Console.run(new Calculator(), 800, 600);
	}

}
