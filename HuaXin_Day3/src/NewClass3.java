import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class NewClass3 {
	public static void main(String[] args){
		File file=new File("E:\\mydata2.txt");
		int n=Integer.parseInt(JOptionPane.showInputDialog("请输入取完数的范围"));
		try {
			BufferedWriter output=new BufferedWriter(new FileWriter(file));
			for(int i=1;i<=n;i++){
				int s,j;
				for(s=0,j=1;j<i/2;j++){
					if(i%j==0)
						s+=j;
				if(s==i){
					System.out.println(i);
					output.flush();
					output.newLine(); 
					output.write(i);
				}
			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
