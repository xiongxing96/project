import javax.swing.JOptionPane;

public class question {
	public static void main(String[] args){
		
		        float x =Float.parseFloat(JOptionPane.showInputDialog("请输入x"));//输入
		        float y =Float.parseFloat(JOptionPane.showInputDialog("请输入y"));//输入
				
				//if((x-2)*(x-2)+(y-2)*(y-2)<=1 ||(x-2)*(x-2)+(y+2)*(y+2)<=1 ||(x+2)*(x+2)+(y-2)*(y-2)<=1||(x+2)*(x+2)+(y+2)*(y+2)<=1)
				if(Math.sqrt(Math.pow(Math.abs(x)-2,2)+Math.pow(Math.abs(y)-2,2))<=1)
					JOptionPane.showMessageDialog(null, "yes");//输出
				else 
					JOptionPane.showMessageDialog(null, "no");//输出

	}
}
