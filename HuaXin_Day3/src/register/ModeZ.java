package register;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ModeZ extends JFrame implements ActionListener{
	private Container con;
	private JLabel labelId,labelName,labelSex,labelDay,labelHobby,labelNum,labelNote;
	private JTextField textId,textName,textNum;
	private JRadioButton rbBoy,rbGirl;
	private ButtonGroup chSex;
	private JComboBox chY,chM,chD;
	private JTextArea textNote;
	private JPanel p1,p2,p3;
	private JCheckBox hb1,hb2,hb3,hb4,hb5;
	private JButton btSave,btReset,btBack;
	private String[] a={"1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003"};
	
	public ModeZ(){
		super("添加员工信息");
		p1=new JPanel();
		p1.setBackground(Color.lightGray);
		p1.setPreferredSize(new Dimension(60,400));
		p2=new JPanel();
		p2.setPreferredSize(new Dimension(190,400));
		p3=new JPanel();
		p3.setLayout(new FlowLayout());
		p3.setPreferredSize(new Dimension(255,60));
		
		con=getContentPane();
		con.setLayout(new FlowLayout());
		GridLayout gly=new GridLayout(7,1);
		p1.setLayout(gly);
		p2.setLayout(gly);
		con.add(p1);
		con.add(p2);
		con.add(p3);
		
		labelId=new JLabel("id");
		p1.add(labelId);
		
		textId=new JTextField(17);
		p2.add(textId);
		textId.setText(String.valueOf((int)(Math.random()*10000)));
		
		labelName=new JLabel("姓名");
		p1.add(labelName);
		
		textName=new JTextField(17);
		p2.add(textName);
		
		labelSex=new JLabel("性别");
		p1.add(labelSex);
		
		rbBoy=new JRadioButton("男");
		rbGirl=new JRadioButton("女");
		chSex=new ButtonGroup();
		chSex.add(rbBoy);
		chSex.add(rbGirl);
		JPanel pa=new JPanel();
		pa.setLayout(new FlowLayout());
		p2.add(pa);
		pa.add(rbBoy);
		pa.add(rbGirl);
		
		labelDay=new JLabel("出生年月");
		p1.add(labelDay);
		JPanel pb=new JPanel();
		pb.setLayout(new FlowLayout());
		p2.add(pb);
		
		chY=new JComboBox<>(a);
		pb.add(chY);
		chM=new JComboBox<>();
		pb.add(chM);
		chD=new JComboBox<>();
		pb.add(chD);
		
		labelHobby=new JLabel("爱好");
		p1.add(labelHobby);
		
		hb1=new JCheckBox("篮球");
		hb2=new JCheckBox("足球");
		hb3=new JCheckBox("羽毛球");
		hb4=new JCheckBox("游泳");
		hb5=new JCheckBox("电竞");
		JPanel pc=new JPanel();
		pc.setLayout(new FlowLayout());
		p2.add(pc);
		pc.add(hb1);
		pc.add(hb2);
		pc.add(hb3);
		pc.add(hb4);
		pc.add(hb5);
		
		labelNum=new JLabel("电话");
		p1.add(labelNum);
		
		textNum=new JTextField();
		p2.add(textNum);
		
		labelNote=new JLabel("备注");
		p1.add(labelNote);
		
		textNote=new JTextArea();
		p2.add(textNote);
		
		btSave=new JButton("保存");
		btReset=new JButton("重置");
		btBack=new JButton("返回");
		btSave.addActionListener(this);
		btReset.addActionListener(this);
		btBack.addActionListener(this);
		p3.add(btSave);
		p3.add(btReset);
		p3.add(btBack);
		
		setSize(280,520);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		Object source=e.getSource();
		if(source==btSave){
			
		}
		else if(source==btReset){
			textId.setText(String.valueOf((int)(Math.random()*10000)));
			textName.setText("");
			rbBoy.setSelected(false);
			rbGirl.setSelected(false);
			hb1.setSelected(false);
			hb2.setSelected(false);
			hb3.setSelected(false);
			hb4.setSelected(false);
			hb5.setSelected(false);
			
		}
	}
	
	public static void main(String[] args) {
		ModeZ w=new ModeZ();
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}