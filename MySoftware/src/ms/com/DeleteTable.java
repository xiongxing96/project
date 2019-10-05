package ms.com;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DeleteTable extends JFrame{
	private Container container;
	private JButton command;
    private JTextField text1;
    private JLabel label1;
    
    public DeleteTable(){
    	super("删除 员工信息");
    	container=getContentPane();
    	container.setLayout(new FlowLayout());
    	
    	label1=new JLabel("输入员工ID");
    	container.add(label1);
    	text1=new JTextField(10);
    	container.add(text1);
    	command=new JButton("点击删除");
    	command.addActionListener(new ActionListener() {
			
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
						BufferedReader input=new BufferedReader(new FileReader(file.getAbsolutePath()));
						
						String tempV=null;
						String[] a;
						ArrayList list =new ArrayList();
						int flag=0;
						while((tempV=input.readLine())!=null){
							a=tempV.split(" ");
							System.out.println(a[0]);
							if(temp.equals(a[0]))
							{
//								System.out.println(tempV);
								flag=1;
							}else
								list.add(tempV);
							
						}
						BufferedWriter output = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
						for( int i=0;i<list.size();i++ ){
						    // System.out.println("list["+i+"]"+list.get(i));
						     output.write(list.get(i).toString());
						     output.newLine();
						    }
						    output.flush();
						    output.close();
						    
							if(flag==1){JOptionPane.showConfirmDialog(null,"该员工信息已删除", "友情提示",JOptionPane.OK_OPTION);}
							else  JOptionPane.showConfirmDialog(null, "输入员工ID未注册或该ID非法 ","友情提示",JOptionPane.WARNING_MESSAGE);
							
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
    	container.add(command);
    	
    	setSize(500,300);
//    	setVisible(true);
    }

    
//    public static void main(String args[]){
//    	DeleteTable w=new DeleteTable();
//    	w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
    
    
}