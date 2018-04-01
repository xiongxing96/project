package Test;


public class Main {
	public static void main(String[] args){
		int[] array0 ={-1,-5,-3,-6,-8};
		int[] array1 = {-2,11,-4,13,-5,-2};
		int[] array2={1,-3,7,8,-4,12,-10,6};
		Maximum Ma = new Maximum();
		System.out.println("MaxSubSum0="+Ma.maxSubSum(array0));
		System.out.println("MaxSubSum1="+Ma.maxSubSum(array1));
		System.out.println("MaxSubSum2="+Ma.maxSubSum(array2));
	}

}
