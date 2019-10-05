package com.java1996.cc;

import org.junit.Test;

import com.java1996.cc.JudgeYear;

import static org.junit.Assert.*;
public class JudgeYearTest {
	
	@Test
	public void Judge1(){
		assertEquals(false,new JudgeYear().Judge(1997));
	}
	
	@Test
	public void Judge2(){
		assertEquals(true,new JudgeYear().Judge(2004));
	}
	
	@Test
	public void Judge3(){
		assertEquals(true,new JudgeYear().Judge(2000));
	}
	
	@Test
	public void Judge(){
		assertEquals(false,new JudgeYear().Judge(2018));	
	}
}
