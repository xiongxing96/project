package register;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

public class Test extends JFrame{
    public Test(BufferedReader T,int row){
        super();
        setTitle("员工信息");
      
        setBounds(200,200,600,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String[] columnNames = {"员工ID","姓名", "性别","出生日期","爱好","电话","备注"};// 定义表格列名数组
        
  
//        File file=new File("E:\\mydata.txt");
//        BufferedReader input=null;
//		int row=-1;
//			try {
//				while(input.readLine()!=null){
//					row++;
//					}
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//	
//        System.out.println(row);
//        
        
        
        String tempV=new String();
        String[][] tableValues=new String[row][7];
        try {
        	int count=0;
			while((tempV=T.readLine())!=null && row--!=0){
				String[] s=tempV.split(" ");
				String a=new String(s[0]);
				System.out.println(tempV);
				System.out.println(s[0]);
				for(int i=0;i<7;i++)
						tableValues[count][i]=new String(s[i]);
				count++;
				}
			System.out.println(count);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
         
        JTable table = new JTable(tableValues,columnNames);
        // 创建显示表格的滚动面板
        JScrollPane scrollPane = new JScrollPane(table);
        // 将滚动面板添加到边界布局的中间
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
    public static void main(String[] args) {
        // TODO 自动生成的方法存根
    	File file =new File("E:\\mydata.txt");
        try {
			BufferedReader T=new BufferedReader(new FileReader(file));
				
			Test frame = new Test(T,20);
	        
	        frame.setVisible(true);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

}
