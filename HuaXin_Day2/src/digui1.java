
public class digui1 {
	public static void main(String[] args) {
		System.out.println(f(20));
	}

 public static int f(int m)
 {
	 if(m==1||m==2)
		 return 1;
	 else 
	    return f(m-1)+f(m-2);	
	
 }
}