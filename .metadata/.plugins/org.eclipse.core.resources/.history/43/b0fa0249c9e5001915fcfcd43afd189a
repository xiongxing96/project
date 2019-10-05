package leetcode0_50;
/**
 * 返回最长回文子串
 */
import Palindrome_string.Solution2;

public class T5 {
	//左右扩展回文串，返回最长子串
		public String findPalindrome(String s, int left, int right) {  
	        int n = s.length();  
	        int l = left;  
	        int r = right;  
	        while (l >= 0 && r <= n - 1 && s.charAt(l) == s.charAt(r)) {  //不能把运行结果置于判断条件中，会混乱最终结果
	            l--;  
	            r++;  
	        }  
	        //int m=l+1;
	        //System.out.println("l:"+m +"  and r:"+r);
	        return s.substring(l + 1, r);  //substring(begin,end)函数，首字母为0， 从begin开始至end前一个
	        //加一是为了跳过0
	    }  
		//寻找最长回文子串函数
	    public String longestPalindrome(String s) {  
	        int n = s.length();  
	        if (n<=1) return s;  
	  
	        String longest = "";  
	  
	        String str; 
	        for (int i=0; i<n-1; i++) { 
	        	//奇数个回文
	            str = findPalindrome(s, i, i);  
	            if (str.length() > longest.length()){  
	                longest = str;  
	            }  
	            //偶数个回文
	            str = findPalindrome(s, i, i + 1);  
	            if (str.length() > longest.length()){  
	                longest = str;  
	            }  
	        }  
	        return longest;  
	    }  
	    //main函数
	    public static void main(String[] args) {
	    	Solution2 so = new Solution2();
	    	System.out.println(so.longestPalindrome("abbbbcdd"));
	    	//System.out.println("afhgsgdjk".substring(1,3));
	    }
}
