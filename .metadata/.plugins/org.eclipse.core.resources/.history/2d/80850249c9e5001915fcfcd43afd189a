package leetcode0_50;

import java.util.*;

/**
 * 二分法查找元素起始和终点下标
 * @author xx
 *
 */
public class T34 {
	public int[] searchRange(int[] nums,int target) {
		int []index = new int[2];
		int start = 0;
		int end = nums.length - 1;
		while(start <= end) {
			while(start >= 0 && nums[start] == target) {
				start--;
			}
			while(end < nums.length && nums[end] == target) {
				end++;
			}
			if(nums[start]==target && nums[end]==target) {
					index[0]=start;
					index[1]=end;
					return index;
			}
			int mid = (start + end)/2;
			if(target < nums[mid]) {
				end = mid - 1;
			}
			else {
				start = mid + 1;
			}
		}
		index[0]=-1;
		index[1]=-1;
		return index;
	}
	public static void main(String[] args) {
		int [] nums = {1,2,3,4,5,6,6,6,7,8};
		int target = 6;
		//int []index = new T34().searchRange(nums, target);
		System.out.println(new T34().searchRange(nums, target));
	}
}
