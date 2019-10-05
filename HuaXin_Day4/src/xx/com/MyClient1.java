package xx.com;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class MyClient1 extends  JFrame{

	private JButton command1;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket client;
	private JTextField text1=null;
	
	public MyClient1(){
		super("自定义客户一");
		Container con=getContentPane();
		command1=new JButton("连接服务器");
		command1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					client=new Socket(InetAddress.getByName("127.0.0.1"), 12553);  //服务器的IP地址，端口号（端口号是程序入口）
					input=new ObjectInputStream(client.getInputStream());//套接字
					try {
						text1.setText(String.valueOf(input.readObject()));
						if(text1.getText().trim()!=null)
							JOptionPane.showConfirmDialog(null, "连接服务器成功","连接服务器中...",JOptionPane.OK_OPTION);
						else
							JOptionPane.showConfirmDialog(null, "连接服务器失败","连接服务器中...",JOptionPane.WARNING_MESSAGE);
						
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		con.setLayout(new FlowLayout());
		con.add(command1);
		text1=new JTextField(30);
		con.add(text1);
		setSize(500,300);
		setVisible(true);
	}
	public  static void main(String[] args){
		MyClient1 w=new MyClient1();
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
