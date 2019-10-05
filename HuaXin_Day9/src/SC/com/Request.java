package SC.com;


import java.io.IOException;
import java.io.InputStream;
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
class Request {
    private InputStream input;
    public Request(InputStream input) {
        this.input=input;
    }
    public String getUri(){
        String content=null,str=null;
        StringBuffer request=new StringBuffer();
        byte[] buffer=new byte[2048];
        int i=0;
        try {
            i=input.read(buffer);//从输入流(即客户端浏览器发送出来的数据),放入字节型数组中
        } catch (IOException ex) {
            Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int k=0;k<i;k++)
            request.append((char)buffer[k]);//将字节数组中内容,转换成字符串
        content=request.toString();
        System.out.println(content);
        if(content!=null)
            return getFilename(content);
        else
            return null;
    }

    private String getFilename(String content) {//这个方法的主要作用是从客端发送的信息中解析出要访问的文件名
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       int a,b;
       a=content.indexOf(" ");
       if(a!=-1){
           b=content.indexOf("?",a+1);
           if(b==-1)b=content.indexOf(" ",a+1);
           return content.substring(a+2,b);
       }
       return null;
    }
    
}
