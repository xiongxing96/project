package leetcode0_50;
/**
 * ×Ö·û´®×ª»»Ð¡Êý
 * @author xx
 *
 */
public class T8 {
	public int myAntoi(String str) {
		str = str.trim();
		int flag = 1;
		long res = 0;
		//String s = "";
		int i = 0;
		if(str.length()==0)return 0;
		if(str.charAt(0)=='-') {
			flag = -1;
			i++;
		}
		if(str.charAt(0)=='+') {
			flag = 1;
			i++;
		}
		//if(flag==-1) res/=-1;
		while(i < str.length()) {
			if(str.charAt(i) <= '9' && str.charAt(i) >= '0') {
				res = res*10+ (str.charAt(i)-'0');
				if(res*flag >= Integer.MAX_VALUE ) {
					return Integer.MAX_VALUE;
				}
				if(res*flag <= Integer.MIN_VALUE) {
					return Integer.MIN_VALUE;
				}
				//s+=str.charAt(i);
				i++;
			}else {
				break;
			}
		}
		if(i==0)return 0;
		return (int)res*flag;
	}
	public static void main(String[] args) {
		//System.out.println(Integer.MAX_VALUE);
		System.out.println(new T8().myAntoi("2147483648"));
	}
}
