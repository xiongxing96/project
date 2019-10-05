package register;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Mode1 extends JFrame{
	private ModeZ window1=null;
	private ModeS window2=null;
	//private ModeG window3=null;
	private ModeC window4=null;
	private JButton command1,command2,command3,command4;
	private Container con;
	
	public Mode1(){
		super("员工信息处理");
		con=getContentPane();
		con.setLayout(new FlowLayout());
		
		command1=new JButton("添加新员工信息");
		command1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(window1==null){
					window1=new ModeZ();
					window1.setVisible(true);
				}else{
					window1.setVisible(true);
				}
				
			}
		});
		command2=new JButton("删除员工信息");
		command2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(window2==null){
					window2=new ModeS();
					window2.setVisible(true);
				}else{
					window2.setVisible(true);
				}
				
			}
		});
		command3=new JButton("修改员工信息");
	/*	command3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(window3==null){
					window3=new ModeG();
					window3.setVisible(true);
				}else{
					window3.setVisible(true);
				}
				
			}
		});
		*/
		command4=new JButton("查找员工信息");
		command4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(window4==null){
					window4=new ModeC();
					window4.setVisible(true);
				}else{
					window4.setVisible(true);
				}
				
			}
		});
		con.add(command1);
		con.add(command2);
		con.add(command3);
		con.add(command4);

		setSize(500,300);
		setVisible(true);
	}
	public static void main(String[] args){
		Mode1 w=new Mode1();
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
