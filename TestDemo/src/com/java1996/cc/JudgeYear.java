package com.java1996.cc;

public class JudgeYear {

	public boolean Judge(int year){
		if(year %4==0 && year%100!=0 || year%400==0)
			return true;
		else
			return false;
	}
}
