package com.java1996.cn;

import java.util.Scanner;

public class People {
	private int location;//所在位置
	private int Goto; //前往第几层
	private boolean condition;//上下楼标记
	private int people_count;//总人数 
	private int quality;//总重量
	public void set_location(int loc){
		location = loc;
	}
	public void set_Goto(int Go){
		Goto = Go;
	}
	public int get_location(){
		return location;
	}
	public int get_Goto(){
		return Goto;
	}
	public boolean get_condition(){
		return condition;
	} 
	public void peopleset(People people){
		Scanner scanner = new Scanner(System.in);
		/*String loc = scanner.nextLine();
		try{
			people.location = Integer.parseInt(loc);
		}catch (Exception e) {
			System.out.println("help");
		}finally {
		}*/
		System.out.println("请输入乘客当前所在楼层：");
		people.location = scanner.nextInt();
		System.out.println("请输入乘客要前往的目的楼层：");
		people.Goto = scanner.nextInt();
		if(people.get_Goto()-people.get_location()>=0)
			people.condition = true;
		else
			people.condition = false;
		
		System.out.println("乘客在第"+people.get_location()+"层楼等电梯");
		System.out.println("乘客要前往第"+people.get_Goto()+"层楼");
	}
	
}
