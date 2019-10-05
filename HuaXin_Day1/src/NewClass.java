
public class NewClass {
	public static void main(String args[]){
		String aString="ABCDFHJ";
		System.out.println(aString.matches("[a-zA-Z]*"));//{a,b}
		String bString="123.45";
		System.out.println(bString.matches("[0-9]*//.[0-9]*"));
	}
}
