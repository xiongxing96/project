package com.second.xx;

public class Max_Subarray {

	public static int MaxSubSum(int[] a){
		    int max_ending_here=0;
		    int max_so_far=0;
		
		for(int i = 0; i < a.length; i ++)
			max_ending_here =Math.max(0,max_ending_here +a[i]);
			max_so_far =Math.max(max_so_far, max_ending_here);
		return max_so_far;
	}	
}
