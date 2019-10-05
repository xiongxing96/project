package ms.com;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AddTable extends JFrame implements ActionListener{
	private Container con;
	private JLabel labelId,labelName,labelSex,labelDay,labelHobby,labelNum,labelNote;
	private JTextField textId,textName,textNum;
	private JRadioButton rbBoy,rbGirl;
	private ButtonGroup chSex;
	private JComboBox chY,chM,chD;
	private JTextArea textNote;
	private JPanel p1,p2,p3;
	private JCheckBox hb1,hb2,hb3,hb4,hb5;
	private JButton btSave,btReset;
	private String[] a={"1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003"};
	private String[] m={"1","2","3","4","5","6","7","8","9","10","11","12"};
	private String[] dd={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17",
			"18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	private String[] xd={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17",
			"18","19","20","21","22","23","24","25","26","27","28","29","30"};
	private String[] r2d={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17",
			"18","19","20","21","22","23","24","25","26","27","28","29"};
	private String[] p2d={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17",
			"18","19","20","21","22","23","24","25","26","27","28"};
	private File f=new File("d:/data.txt");//向指定文本框内写入
	
	
	public AddTable(){
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
		textName.setText("");
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
		chM=new JComboBox<>(m);
		pb.add(chM);
		chD=new JComboBox<>(dd);
		chD.addActionListener(this);
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
		textNum.setText("");
		p2.add(textNum);
		
		labelNote=new JLabel("备注");
		p1.add(labelNote);
		
		textNote=new JTextArea();
		textNote.setText("");
		p2.add(textNote);
		
		btSave=new JButton("保存");
		btReset=new JButton("重置");
		btSave.addActionListener(this);
		btReset.addActionListener(this);
		p3.add(btSave);
		p3.add(btReset);
		
		setSize(280,520);
//		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		Object source=e.getSource();
		if(source==btSave){
			fun();
			JOptionPane.showMessageDialog(null,"数据已保存","完成",JOptionPane.DEFAULT_OPTION);
		}
		else if(source==btReset){
			textId.setText(String.valueOf((int)(Math.random()*10000)));
			textName.setText("");
			chSex.clearSelection();
			hb1.setSelected(false);
			hb2.setSelected(false);
			hb3.setSelected(false);
			hb4.setSelected(false);
			hb5.setSelected(false);
			textNum.setText("");
			textNote.setText("");
			chY.setSelectedIndex(0);
			chM.setSelectedIndex(0);
			chD.setSelectedIndex(0);
		}
//		else if(source==chD){
//			int y=Integer.parseInt(a[chY.getSelectedIndex()]);
//			if((y%4==0&&y%100!=0)||y%400==0){
//				int x=Integer.parseInt(m[chM.getSelectedIndex()]);
//				if(x==2){
//					chD.removeAllItems();
//					chD.addItem(r2d);
//				}
//				else if(x==1||x==3||x==5||x==7||x==8||x==10||x==12){
//					chD.removeAllItems();
//					chD.addItem(dd);
//				}
//				else{
//					chD.removeAllItems();
//					chD.addItem(xd);
//				}
//			}
//			else{
//				int x=Integer.parseInt(m[chM.getSelectedIndex()]);
//				if(x==2){
//					chD.removeAllItems();
//					chD.addItem(r2d);
//				}
//				else if(x==1||x==3||x==5||x==7||x==8||x==10||x==12){
//					chD.removeAllItems();
//					chD.addItem(dd);
//				}
//				else{
//					chD.removeAllItems();
//					chD.addItem(xd);
//				}
//			}
//		}
	}
	
	public String getId(){
		return textId.getText()+" ";
	}
	public String getName(){
		return textName.getText()+" ";
	}
	public String getSex(){
		if(rbBoy.isSelected())
			return "Boy ";
		else if(rbGirl.isSelected())
			return "Girl ";
		else
			return "null ";
	}
	public String getDay(){
		String str="";
		str=chY.getSelectedItem().toString()+"-"+chM.getSelectedItem().toString()+"-"+chD.getSelectedItem().toString()+" ";
		return str;
	}
	public String getHobby(){
		String str="";
		if(hb1.isSelected())
			str+=hb1.getText();
		if(hb2.isSelected()){
			if(str.length()>0)
				str+=","+hb2.getText();
			else if(str.length()==0)
				str+=hb2.getText();
		}
		if(hb3.isSelected()){
			if(str.length()>0)
			str+=","+hb3.getText();
			else if(str.length()==0)
				str+=hb3.getText();
		}
		if(hb4.isSelected()){
			if(str.length()>0)
			str+=","+hb4.getText();
			else if(str.length()==0)
				str+=hb4.getText();
		}
		if(hb5.isSelected()){
			if(str.length()>0)
			str+=","+hb5.getText();
			else if(str.length()==0)
				str+=hb5.getText();
		}
		return str+" ";
	}
	
	public String getNum(){
		if(textNum.getText().length()>0)
			return textNum.getText()+" ";
		else 
			return "null ";
	}
	public String getNote(){
		return textNote.getText().toString()+"\r\n";
	}
	
	public void fun() {
		write(getId());
		if(getName().equals(" ")){
			JOptionPane.showMessageDialog(null,"请输入姓名~","错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		else{ 
			write(getName());
			if(getSex().compareTo("null ")==0){
				JOptionPane.showMessageDialog(null,"请输入性别~","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		    else{ 
		    	write(getSex());
		    	write(getDay());
		    	if(getDay().equals(" ")){
		    		JOptionPane.showMessageDialog(null,"请选择爱好~","错误",JOptionPane.ERROR_MESSAGE);
		    		return;
		    	}
		    	else
		    		write(getHobby());
				if(getNum().compareTo("null ")==0){
					JOptionPane.showMessageDialog(null,"请输入电话号码~","错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
				else{
					write(getNum());
					write(getNote());
				}
		    }
		}
	}
		public void write(String line){
			  try{
				 FileWriter fw=new FileWriter(f,true);
				 fw.write(line);
			   fw.close();
			  }catch(Exception e){
			 
			  }
			 }
	
//	public static void main(String[] args) {
//		AddTable a=new AddTable();
//		a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
}
