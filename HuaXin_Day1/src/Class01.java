import javax.swing.JOptionPane;

public class Class01 {
	public static void main(String[] args){
		
	String number1=JOptionPane.showInputDialog("请输入一个数");//输入

			int n1=Integer.parseInt(number1);

			float n2=Float.parseFloat(number1);
			
			int b =Integer.parseInt(JOptionPane.showInputDialog("请输入另一个数"));//输入

			JOptionPane.showMessageDialog(null, "Hello");//输出

			JOptionPane.showMessageDialog( null, "Hello","我的消息",JOptionPane.ERROR_MESSAGE);//输出
}
}