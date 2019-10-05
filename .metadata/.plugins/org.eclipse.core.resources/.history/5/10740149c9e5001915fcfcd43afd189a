package leetcode0_50;
/**
 * 下标规律法
 * @author xx
 *
 */
public class T6 {
	 public String convert(String s, int numRows) {
		if(numRows == 1)return s;//列数为1直接返回
		String ret="";
	    int step = 2*numRows -2 ;//循环数
	    int index = 0;
	    int add = 0;
	    int len = s.length();
	    for(int i=0;i<numRows;i++) {
	    	index = i;//下标索引
	    	add = i*2;//间距
	    	while (index < len)//超出字符串长度计算下一层
			{
				ret += s.charAt(index); // 当前行的第一个字母
				add = step - add;// 第一次间距是step-2*i，第二次是2*i, 
				index += (i == 0 || i == numRows-1) ? step : add; // 0行和最后一行使用step间距，其余使用add间距
			}
	    }
	    return ret;
	 }
	 public static void main(String[] args) {
		 System.out.println(new T6().convert("LEETCODEISHIRING", 4));
	 } 
}
