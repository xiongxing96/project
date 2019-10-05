import javax.swing.JOptionPane;

public class question2 {
	public static void main(String[] args){
		int a=12321;
		int s,j;
		j=a;
		s=0;
		while(j!=0){
			s=s*10+j%10;
			j/=10;
		}
		if(s==a)
			System.out.println(a+"是一个回文数字");
		else
			System.out.println(a+"不是一个回文数字");
        
}
}
