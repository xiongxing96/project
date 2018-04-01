package Test;

public class Maximum {
	
	public static int maxSubSum(int[] a){

	int maxSum = 0; 
	int tempSum = 0;//临时最大子数列和
	int begin = 0;
	for(int i = 0; i<a.length; i++){
		if(tempSum > 0)
			tempSum += a[i];
		else
		{
			tempSum =a[i];
			begin = i;
		}
		if(tempSum > maxSum){
			maxSum = tempSum;
		}
	}
	return maxSum;	
}
}
