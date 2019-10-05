
public class CaiPiao {
	public static void main(String[] args){
		 int a[]=new int[36];
		 for(int i=1;i<=10;i++){
			 for(int j=0;j<a.length;j++)
				 a[j]=0;
			 int w=0;
			 while(w!=7){
				 int temCode=i+(int)(Math.random()*35);
				 if(a[temCode]==0){
					 w++;
					 a[temCode]=1;
				 }
			 }
			 for(int j=1;j<=a.length;j++)
				 if(a[j]==1)
					 System.out.println(j);
		 }
	}
}
