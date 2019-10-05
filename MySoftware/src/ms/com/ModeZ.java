package ms.com;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ModeZ extends JFrame{
	private JLabel label1,label2,lable3,lable4,label5;
	private JButton command1,command2,command3;
	private JTextField text1,text2,text3,text4;
	private Container container;
	
	public ModeZ(){ 
	super("增加新员工信息");
	container=getContentPane();
	container.setLayout(new FlowLayout());
	
	label1=new JLabel("获取员工号");
	container.add(label1);
	text1=new JTextField(36);
	container.add(text1);
	
	label2=new JLabel("输入姓名");
	container.add(label2);
	text2=new JTextField(38);
	container.add(text2);
	
	lable3=new JLabel("性别");
	container.add(lable3);
	text3=new JTextField(40);
	container.add(text3);
	
	lable4=new JLabel("手机号");
	container.add(lable4);
	text4=new JTextField(39);
	container.add(text4);
	
	lable4=new JLabel("爱好");
	container.add(lable4);
	text4=new JTextField(39);
	container.add(text4);
	
	
	setSize(500,300);
	setVisible(true);
}


public static void main(String args[]){
	ModeZ w=new ModeZ();
	w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
}
