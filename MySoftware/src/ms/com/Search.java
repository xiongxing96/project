package ms.com;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Search extends JFrame{
	private Container container;
	private JButton command1;
    private JTextField text1;
    private JLabel label1;
    
    public Search(int ff){
    	super("查找员工信息");
    	container=getContentPane();
    	container.setLayout(new FlowLayout());
    	
    	label1=new JLabel("输入员工ID");
    	container.add(label1);
    	text1=new JTextField(10);
    	container.add(text1);
    	command1=new JButton("点击查找");
    	command1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String temp=text1.getText().trim();
				if(temp.length()>4)
				{
					JOptionPane.showConfirmDialog(null,"输入员工ID有误", "友情提示",JOptionPane.WARNING_MESSAGE);
				}else{
					
					File file =new File("d:\\data.txt");
					try {
						BufferedReader input=new BufferedReader(new FileReader(file));
						String tempV=new String();
						boolean flag=false;
						String[] a;
						while((tempV=input.readLine())!=null){
							a=tempV.split(" ");
							if(temp.equals(a[0]))
							{
								System.out.println(tempV);
								if(ff==1){
									Info info=new Info(tempV);
									info.setVisible(true);
								}
								else if(ff==0){
									FixTable aa=new FixTable(tempV);
									aa.setVisible(true);
								}
								flag=true;
							}
						}
							if(flag){;}
							else  JOptionPane.showConfirmDialog(null, "输入员工ID未注册或ID非法 ","友情提示",JOptionPane.WARNING_MESSAGE);
							
					} catch (FileNotFoundException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
			}
		});
    	container.add(command1);
    	
    	setSize(500,300);
//    	setVisible(true);
    }
    
//    public static void main(String args[]){
//    	Search w=new Search();
//    	w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
    
    
}
