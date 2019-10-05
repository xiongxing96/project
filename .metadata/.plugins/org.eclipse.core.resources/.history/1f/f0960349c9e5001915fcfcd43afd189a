package leetcode0_50;
/**
 * 判断回文整数
 * @author xx
 *
 */
public class T9 {
	 public boolean isPalindrome(int x) {
		    int temp = x;
	        if(x < 0)return false;
	        if(x >= 0 && x <= 9)return true;
	        int s = 0;
	        while(x >= 10){
	            int y = x % 10;//得到最后一位
	            s = s * 10 + y;      
	            x = x / 10;    
	        }
	        s = s * 10 + x;
	        System.out.println(s);
	        if(s == temp)return true;
	        return false;
	    }
	 public static void main(String[] args) {
		T9 t9  =new T9();
		boolean l = t9.isPalindrome(101);
		System.out.println(l);
	 }
}
