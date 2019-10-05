package SC.com;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class WebServer {
    public static void main(String[] args){
        ServerSocket server=null;
        Socket s=null;
        try {
            server=new ServerSocket(12345,3,InetAddress.getByName("127.0.0.1"));
            while(true){
                s=server.accept();
                OutputStream output=s.getOutputStream();
                InputStream input=s.getInputStream();
                Request request=new Request(input);//自定类,用来处理客户端的请求
                String filename=request.getUri();//通过上一个自定义的类,得到客户端想要访问的服务器上的具体页
               // System.out.println("您要访问的是："+filename);
                Response response=new Response(filename,output);
                response.response();
                
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
