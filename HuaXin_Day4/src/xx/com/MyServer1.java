package xx.com;

import java.awt.Container;
import java.awt.Font;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.omg.PortableInterceptor.IORInterceptor_3_0Holder;

public class MyServer1 extends JFrame{

	private JTextArea edit1;
	private JScrollPane scroll1;
	private int count;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private ServerSocket server;
	private Socket connection;
	private StringBuffer dispS=new StringBuffer();
	public MyServer1(){
		super("自定义服务器一");
		Container con =getContentPane();
		edit1=new JTextArea();
		scroll1=new JScrollPane(edit1);
		Font myfont=new Font("宋体", Font.PLAIN, 20);
		edit1.setFont(myfont);
		edit1.setEditable(false);
		scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		con.add(scroll1);
		setSize(500,600);
		setVisible(true);
	}
	public void runServer(){
		try {
			server=new ServerSocket(12553,100);//服务器端口号 0-65535（建议不使用1024以下的端口号，操作系统使用 ）和最大的连接数量
			while(true){
				waitForConnection();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void waitForConnection() throws IOException{
		dispS.append("正在等待用户的连接...\n");
		edit1.setText(dispS.toString());//显示状态，可有可无
		connection=server.accept();//它是 一个阻塞语句，等待客户端与本服务器发生连接
		output=new ObjectOutputStream(connection.getOutputStream());//创建输出流
		output.writeObject("Welcome here!");//写数据 
		output.flush();
		dispS.append("已有用户与我连接\n");
		edit1.setText(dispS.toString());
		
	}

	public static void main(String[] args){
		MyServer1 w=new MyServer1();
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.runServer();
		
	}
	
}
