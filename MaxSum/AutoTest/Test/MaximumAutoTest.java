package Test;

import static org.junit.Assert.*;

import org.junit.Test;

public class MaximumAutoTest {

	@Test
	public void test(){
		int []a={-1,-5,-3,-6,-8};
		assertEquals(0, new Maximum().maxSubSum(a));
	}
	
	@Test
	public void test1(){
		int []a={-2,11,-4,13,-5,-2};
		assertEquals(20, new Maximum().maxSubSum(a));
	}
	
	@Test
	public void test2(){
		int []a={1,-3,7,8,-4,12,-10,6};
		assertEquals(23, new Maximum().maxSubSum(a));
	}
}
