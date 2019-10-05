import javax.swing.JOptionPane;

public class question1 {
	public static void main(String[] args){
		int n1,n2;
        int a =Integer.parseInt(JOptionPane.showInputDialog("请输入a"));//输入
        int b =Integer.parseInt(JOptionPane.showInputDialog("请输入b"));//输入
        //最小公倍数
		for(n2=a;;n2++){
			if(n2%a==0&&n2%b==0){
				break;
			}
		}
        //最大公约数
        while(b!=0)
        {
        int r=a%b;
        a=b;
        b=r;
        }
        n1=a;
			JOptionPane.showMessageDialog(null, "最小公倍数 是 "+String.valueOf(n2)+","+"最大公约数是"+String.valueOf(n1));//输出
		
	}
}

/*
 	int a=12;
 	int b=14;
 	int c;
 	c=a<b?a:b;
 	while(a%c!=0||b%c!=0)
 	c--;
 	
 	
 	while
*/

/*
 		c=a*b;
 		while(b!=0){
 		t=a%b;
 		a=b;
 		b=t;
 		}
 		
 		min=c/a;
 		max=a
 */