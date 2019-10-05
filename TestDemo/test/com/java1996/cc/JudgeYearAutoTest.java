package com.java1996.cc;

import static org.junit.Assert.*;

import org.junit.Test;

public class JudgeYearAutoTest {

		@Test
		public void testJudge1(){
			assertEquals(false,new JudgeYear().Judge(1997));
		}
		
		@Test
		public void testJudge2(){
			assertEquals(true,new JudgeYear().Judge(2004));
		}
		
		@Test
		public void testJudge3(){
			assertEquals(true,new JudgeYear().Judge(2000));
		}
		
		@Test
		public void testJudge(){
			assertEquals(false,new JudgeYear().Judge(2018));	
		}
}


