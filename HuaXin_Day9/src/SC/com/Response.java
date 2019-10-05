package SC.com;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
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
class Response {
    private OutputStream output;
    private String filename;

    public Response(String filename, OutputStream output) {
        this.output=output;
        this.filename=filename;
    }
    public void response() throws IOException{
        String filepath="d:\\"+filename;
        byte[] buffer=new byte[1024];
        int ch;
        FileInputStream fis=null;
        if(filepath!=null){
            File file=new File(filepath);
            String str="";
            if(file.exists()){
                try {
                    fis=new FileInputStream(file);
                    str="HTTP/1.1 200 OK\r\nContent-Type:text/html\r\n\r\n";
                    output.write(str.getBytes());
                    ch=fis.read(buffer);
                    while(ch!=-1){
                        output.write(buffer,0,ch);
                        ch=fis.read(buffer, 0, 1024);
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Response.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Response.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }else{
                str="HTTP/1.1 404 File Not Found\r\nContent-Type:text/html\r\n\r\n<h1>404 File Not Found</h1>";
                try {
                    output.write(str.getBytes());
                } catch (IOException ex) {
                    Logger.getLogger(Response.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            output.close();
            
        }
        
        
    }
    
}

