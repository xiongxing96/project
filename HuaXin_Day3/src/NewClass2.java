import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

public class NewClass2 {

	public static void main(String[] args){
		/*File file=new File("E:\\mydata1.txt");
		System.out.println(file.exists());//测试文件是否存在
		System.out.println(file.isDirectory());//是否存在该文件夹
		System.out.println(file.getAbsolutePath());//返回路径
		System.out.println(file.getName());//返回文件名
		System.out.println(file.length());//返回字节长度
		
		File file=new File("E:\\www");
		String filename[]=file.list();
		for(int i=0;i<filename.length;i++)
		System.out.println(filename[i]);
		
		
		File file=new File("d:\\mydata.txt");
		try {
			BufferedReader input =new BufferedReader(new FileReader(file));
			String tempV=new String();
			try {
				while((tempV=input.readLine())!=null){
					System.out.println(tempV);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		File file =new File("c:\\xxx.txt");
	}
	
}
