package leetcode0_50;

import java.util.HashMap;
import java.util.Map;

public class T1 {
	    public int[] twoSum(int[] nums, int target) {
	        Map<Integer, Integer> map = new HashMap<>();
	        for(int i = 0; i< nums.length; i++) {
	            if(map.containsKey(target - nums[i])) {
	                return new int[] {map.get(target-nums[i]),i};
	                //将商作为一个key值存储到map中
	            }
	            map.put(nums[i], i);
	        }
	        throw new IllegalArgumentException("No two sum solution");
	    }
	    public static void main(String[] args) {
	    	int []nums = {2,3,5,7};
	    	int target = 1;
	    	int res[] = new T1().twoSum(nums, target);
	    	if(res.length > 1)
	    	System.out.print( res[0]+":"+res[1]);
	    }

}
//class Solution {
//    public int findIndex(int []nums,int i,int temp){
//        for(int j=i+1;j<nums.length;j++){
//            if(temp==nums[j])
//                return j;
//        }
//        return -1;
//    }
//    public int[] twoSum(int[] nums, int target) {
//        int []res = new int[2];
//        for(int i=0;i<nums.length;i++){
//            int temp = target-nums[i];
//            res[0] = i;
//            int flag = 0;
//            int index = findIndex(nums,i,temp); 
//            if(index > 0){
//                res[1] = index;
//                flag = 1;
//                break;
//            }
//        }
//        return res;
//    }
//}