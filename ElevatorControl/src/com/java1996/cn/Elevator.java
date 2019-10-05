package com.java1996.cn;

import java.util.Scanner;

import javax.lang.model.util.ElementKindVisitor6;

public class Elevator {
	private int condition;//电梯状态
	private int ele_num;//电梯号码
	private int currentpose;//电梯当前位置
	private int weight_limit;//电梯限重
	private int number_limit;//电梯限数
 
	
	//返回值
	public int get_conditionvalue(){
		return condition;
	}
    public  String get_Condition(){
    	if(condition == -1) return "DOWN";
    	if(condition == 0) return "STOP";
    	if(condition == 1) return "UP";
		return null;
    }
	public int get_ele_num(){
		return ele_num;
	}
	public int get_currentpose(){
		return currentpose;
	}
	

	//设置值
	public void set_condition(int con){
		condition=con;
	}
	public void set_ele_num(int num){
		ele_num=num;
	}
	public void set_currentpose(int pose){
		currentpose=pose;
	}

	public  void elevatorset1( Elevator elevator){
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("设置电梯当前位置:");
		elevator.currentpose = scanner.nextInt();
		System.out.println("设置电梯当前状态：");
		elevator.condition = scanner.nextInt();
		
		System.out.println("当前电梯");
		System.out.println("当前正在第"+elevator.get_currentpose()+"层楼");
		System.out.println("电梯运行状态为"+elevator.get_Condition()+"\n");
	}
	
}
