
public class digui2 {
	public static void main(String[] args){
		System.out.println(fun(2,3));
	}
	
	public static int fun(int x,int y){
		if(y==0)
			return 1;
		return fun(x, y-1)*x;
	}
}
