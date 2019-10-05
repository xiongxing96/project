package com.java1996.cn;

import java.awt.geom.FlatteningPathIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.print.attribute.standard.PrinterLocation;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class Test {
	public static void main(String[] arg){
		Elevator elevator1 = new Elevator();
		System.out.println("请设置elevator1：");
		elevator1.elevatorset1(elevator1);
		
		Elevator elevator2 = new Elevator();
		System.out.println("请设置elevator2：");
		elevator2.elevatorset1(elevator2);
		
		Elevator elevator3 = new Elevator();
		System.out.println("请设置elevator3：");
		elevator3.elevatorset1(elevator3);
		
		Elevator  elevator4 = new Elevator();
		System.out.println("请设置elevator4：");
		elevator4.elevatorset1(elevator4);
		
		/*elevator1.set_ele_num(1);
		elevator1.set_currentpose(20);
		elevator1.set_condition(0);
		System.out.println("第"+elevator1.get_ele_num()+"号电梯");
		System.out.println("当前正在第"+elevator1.get_currentpose()+"层楼");
		System.out.println("电梯运行状态为"+elevator1.get_Condition()+"\n");


		Elevator elevator2 = new Elevator();
		elevator2.set_ele_num(2);
		elevator2.set_currentpose(5);
		elevator2.set_condition(0);
		System.out.println("第"+elevator2.get_ele_num()+"号电梯");
		System.out.println("当前正在第"+elevator2.get_currentpose()+"层楼");
		System.out.println("电梯运行状态为"+elevator2.get_Condition()+"\n");

		
		Elevator elevator3 = new Elevator();
		elevator3.set_ele_num(3);
		elevator3.set_currentpose(6);
		elevator3.set_condition(0);
		System.out.println("第"+elevator3.get_ele_num()+"号电梯");
		System.out.println("当前正在第"+elevator3.get_currentpose()+"层楼");
		System.out.println("电梯运行状态为"+elevator3.get_Condition()+"\n");
	
		Elevator elevator4 = new Elevator();
		elevator4.set_ele_num(4);
		elevator4.set_currentpose(12);
		elevator4.set_condition(0);
		System.out.println("第"+elevator4.get_ele_num()+"号电梯");
		System.out.println("当前正在第"+elevator4.get_currentpose()+"层楼");
		System.out.println("电梯运行状态为"+elevator4.get_Condition()+"\n");
	*/

		
		People people = new People();
		people.peopleset(people);
		//people.set_location(19);
		//people.set_Goto(6);
		/*
		boolean temp;//乘客上下楼标记
		if(people.get_Goto()-people.get_location()>=0)
			temp = true;
		else
			temp = false;
		*/
		//System.out.println("乘客在第"+people.get_location()+"层楼等电梯");
		//System.out.println("乘客要前往第"+people.get_Goto()+"层楼");
		
		boolean flag1; //乘客所处楼层奇偶标记
		
		if(people.get_location()%2==1){
			flag1 = true;
			System.out.println("\n出发楼层处于奇数层，电梯3不能到达");
		}else{
			flag1 = false;
			System.out.println("\n出发楼层处于偶 数层，电梯2不能到达");
		}
		boolean flag2; //目的地楼层奇偶标记
		if(people.get_Goto()%2==1){
			flag2 = true;
			System.out.println("目的地楼层为奇数层，电梯3不能到达\n");
		}else{
			flag2 = false;
			System.out.println("目的地楼层为偶数层，电梯2不能到达\n");
		}
		//给数组赋初值，用于存放乘客与电梯的距离
		int[] a = new int[5];//同方向或停止的电梯
		for(int i=0;i<5;i++)
			a[i]=50;
		int[] b = new int[5];//反方向电梯
		for(int i=0;i<5;i++)
			b[i]=50;
			

		if(flag1 && flag2 ){
			if(people.get_condition()){
				if(elevator1.get_currentpose() <= people.get_location()&&(elevator1.get_conditionvalue()==1||elevator1.get_conditionvalue()==0))
				{
					a[1]=people.get_location()-elevator1.get_currentpose();
				}else{
					b[1]=elevator1.get_currentpose()-people.get_location();
				}
				/////
				if(elevator2.get_currentpose()<=people.get_location()&&(elevator2.get_conditionvalue()==1||elevator2.get_conditionvalue()==0))
				{
					a[2]=people.get_location()-elevator2.get_currentpose();
				}else{
					b[2]=elevator2.get_currentpose()-people.get_location();
				}
				////
				if(elevator4.get_currentpose()<=people.get_location()&&(elevator4.get_conditionvalue()==1||elevator4.get_conditionvalue()==0))
				{
					a[4]=people.get_location()-elevator4.get_currentpose();
				}else{
					b[4]=elevator4.get_currentpose()-people.get_location();
				}
				////
				Map<String, Integer>map = new HashMap<String,Integer>();
				int k1 = Math.min(Math.min(a[1],a[2]),Math.min(a[3], a[4]));
				int	k2 = Math.min(Math.min(b[1],b[2]),Math.min(b[3], b[4]));

				if(k1<50)
				{
					map.put("elevator1",a[1]);
					map.put("elevator2",a[2]);
					map.put("elevator4",a[4]);
					int value=k1;
					String key="";  
					for (Map.Entry<String, Integer> entry : map.entrySet()) {  
						if(value==entry.getValue()){  
							key=entry.getKey();  
						}  
					}  
					System.out.println(key+"即将到达");	
				}//endif
				else{
					map.put("elevator1",b[1]);
					map.put("elevator2",b[2]);
					map.put("elevator4",b[4]);
					int value=k2;
					String key="";  
					for (Map.Entry<String, Integer> entry : map.entrySet()) {  
						if(value==entry.getValue()){  
							key=entry.getKey();  
						}  
					}   
					System.out.println(key+"即将到达");	
				}
			}else{
				if(elevator1.get_currentpose() >= people.get_location()&&(elevator1.get_conditionvalue()==-1||elevator1.get_conditionvalue()==0))
				{
					a[1]=elevator1.get_currentpose()-people.get_location();
				}else{
					b[1]=people.get_location()-elevator1.get_currentpose();
				}	   
				/////
				if(elevator2.get_currentpose()>=people.get_location()&&(elevator2.get_conditionvalue()==-1||elevator2.get_conditionvalue()==0))
				{
					a[2]=elevator2.get_currentpose()-people.get_location();
				}else{
					b[2]=people.get_location()-elevator2.get_currentpose();
				}
				////
				if(elevator4.get_currentpose()>=people.get_location()&&(elevator4.get_conditionvalue()==-1||elevator4.get_conditionvalue()==0))
				{
					a[4]=elevator4.get_currentpose()-people.get_location();
				}else{
					b[4]=people.get_location()-elevator4.get_currentpose();
				}
				////
				Map<String, Integer>map = new HashMap<String,Integer>();
				int k1 = Math.min(Math.min(a[1],a[2]),Math.min(a[3], a[4]));
				int	k2 = Math.min(Math.min(b[1],b[2]),Math.min(b[3], b[4]));

				if(k1<50)
				{
					map.put("elevator1",a[1]);
					map.put("elevator2",a[2]);
					map.put("elevator4",a[4]);
					int value=k1;
					String key="";  
					for (Map.Entry<String, Integer> entry : map.entrySet()) {  
						if(value==entry.getValue()){  
							key=entry.getKey();  
						}  
					}  
	     
					System.out.println(key+"即将到达");	
				}
				else{
					map.put("elevator1",b[1]);
					map.put("elevator2",b[2]);
					map.put("elevator4",b[4]);
					int value=k2;
					String key="";  
					for (Map.Entry<String, Integer> entry : map.entrySet()) {  
						if(value==entry.getValue()){  
							key=entry.getKey();  
						}  
					}   
					System.out.println(key+"即将到达");	
		  	    	}    		
			}//endelse
			}
			/////////////////////////////////////////////////////////////////////////////////
		else if(!flag1 && !flag2){
		
			if(people.get_condition()){
				if(elevator1.get_currentpose() <= people.get_location()&&(elevator1.get_conditionvalue()==1||elevator1.get_conditionvalue()==0))
				{
					a[1]=people.get_location()-elevator1.get_currentpose();
				}else{
					b[1]=elevator1.get_currentpose()-people.get_location();
				}
				/////
				if(elevator3.get_currentpose()<=people.get_location()&&(elevator3.get_conditionvalue()==1||elevator3.get_conditionvalue()==0))
				{
					a[3]=people.get_location()-elevator3.get_currentpose();
				}else{
					b[3]=elevator3.get_currentpose()-people.get_location();
				}
				/////
	
				if(elevator4.get_currentpose()<=people.get_location()&&(elevator4.get_conditionvalue()==1||elevator4.get_conditionvalue()==0))
				{
					a[4]=people.get_location()-elevator4.get_currentpose();
				}else{
					b[4]=elevator4.get_currentpose()-people.get_location();
				}
				////

	
				Map<String, Integer>map = new HashMap<String,Integer>();
				int k1 = Math.min(Math.min(a[1],a[2]),Math.min(a[3], a[4]));
				int	k2 = Math.min(Math.min(b[1],b[2]),Math.min(b[3], b[4]));

				if(k1<50)
				{
					map.put("elevator1",a[1]);
					map.put("elevator3",a[3]);
					map.put("elevator4",a[4]);
					int value=k1;
					String key="";  
					for (Map.Entry<String, Integer> entry : map.entrySet()) {  
						if(value==entry.getValue()){  
							key=entry.getKey();  
						}  
					}  
 
					System.out.println(key+"即将到达");	
				}//endif
				else{
					map.put("elevator1",b[1]);
					map.put("elevator3",b[3]);
					map.put("elevator4",b[4]);
					int value=k2;
					String key="";  
					for (Map.Entry<String, Integer> entry : map.entrySet()) {  
						if(value==entry.getValue()){  
							key=entry.getKey();  
						}  
					}   
					System.out.println(key+"即将到达");	
				}
			}else{
				if(elevator1.get_currentpose() >= people.get_location()&&(elevator1.get_conditionvalue()==-1||elevator1.get_conditionvalue()==0))
				{
					a[1]=elevator1.get_currentpose()-people.get_location();
				}else{
					b[1]=people.get_location()-elevator1.get_currentpose();
				}
	   
				///
				if(elevator3.get_currentpose()>=people.get_location()&&(elevator3.get_conditionvalue()==-1||elevator3.get_conditionvalue()==0))
				{
					a[3]=elevator3.get_currentpose()-people.get_location();
				}else{
					b[3]=people.get_location()-elevator3.get_currentpose();
				}
				/////
		
				if(elevator4.get_currentpose()>=people.get_location()&&(elevator4.get_conditionvalue()==-1||elevator4.get_conditionvalue()==0))
				{
					a[4]=elevator4.get_currentpose()-people.get_location();
				}else{
					b[4]=people.get_location()-elevator4.get_currentpose();
				}
				////
	
				Map<String, Integer>map = new HashMap<String,Integer>();
				int k1 = Math.min(Math.min(a[1],a[2]),Math.min(a[3], a[4]));
				int	k2 = Math.min(Math.min(b[1],b[2]),Math.min(b[3], b[4]));

				if(k1<50)
				{
					map.put("elevator1",a[1]);
					map.put("elevator3",a[3]);
					map.put("elevator4",a[4]);
					int value=k1;
					String key="";  
					for (Map.Entry<String, Integer> entry : map.entrySet()) {  
						if(value==entry.getValue()){  
							key=entry.getKey();  
						}  
					}  
     
					System.out.println(key+"即将到达");	
				}
				else{
					map.put("elevator1",b[1]);
					map.put("elevator3",b[3]);
					map.put("elevator4",b[4]);
					int value=k2;
					String key="";  
					for (Map.Entry<String, Integer> entry : map.entrySet()) {  
						if(value==entry.getValue()){  
							key=entry.getKey();  
						}  
					}   
					System.out.println(key+"即将到达");	
	  	    }    		
			}//endelse
		
			}//!flag1 && !flag2
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else {
				if(people.get_condition()){
					if(elevator1.get_currentpose() <= people.get_location()&&(elevator1.get_conditionvalue()==1||elevator1.get_conditionvalue()==0))
					{
						a[1]=people.get_location()-elevator1.get_currentpose();
					}else{
						b[1]=elevator1.get_currentpose()-people.get_location();
					}
					/////
					if(elevator4.get_currentpose()<=people.get_location()&&(elevator4.get_conditionvalue()==1||elevator4.get_conditionvalue()==0))
					{
						a[4]=people.get_location()-elevator4.get_currentpose();
					}else{
						b[4]=elevator4.get_currentpose()-people.get_location();
					}
					////

					Map<String, Integer>map = new HashMap<String,Integer>();
					int k1 = Math.min(a[1], a[4]);
					int	k2 = Math.min(b[1], b[4]);

					if(k1<50)
					{
						map.put("elevator1",a[1]);
						map.put("elevator4",a[4]);
						int value=k1;
						String key="";  
						for (Map.Entry<String, Integer> entry : map.entrySet()) {  
							if(value==entry.getValue()){  
								key=entry.getKey();  
							}  
						}  
 
						System.out.println(key+"即将到达");	
					}//endif
					else{
						map.put("elevator1",b[1]);
						map.put("elevator4",b[4]);
						int value=k2;
						String key="";  
						for (Map.Entry<String, Integer> entry : map.entrySet()) {  
							if(value==entry.getValue()){  
								key=entry.getKey();  
							}  
						}   
						System.out.println(key+"即将到达");	
					}
				}else{
					if(elevator1.get_currentpose() >= people.get_location()&&(elevator1.get_conditionvalue()==-1||elevator1.get_conditionvalue()==0))
					{
						a[1]=elevator1.get_currentpose()-people.get_location();
					}else{
						b[1]=people.get_location()-elevator1.get_currentpose();
					}
					////
					if(elevator4.get_currentpose()>=people.get_location()&&(elevator4.get_conditionvalue()==-1||elevator4.get_conditionvalue()==0))
					{
						a[4]=elevator4.get_currentpose()-people.get_location();
					}else{
						b[4]=people.get_location()-elevator4.get_currentpose();
					}
					////
	
		
					Map<String, Integer>map = new HashMap<String,Integer>();
					int k1 = Math.min(a[1],a[4]);
					int	k2 = Math.min(b[1],b[4]);

					if(k1<50)
					{
						map.put("elevator1",a[1]);
						map.put("elevator4",a[4]);
						int value=k1;
						String key="";  
						for (Map.Entry<String, Integer> entry : map.entrySet()) {  
							if(value==entry.getValue()){  
								key=entry.getKey();  
							}  
						}  
     
						System.out.println(key+"即将到达");	
					}
					else{
						map.put("elevator1",b[1]);
						map.put("elevator4",b[4]);
						int value=k2;
						String key="";  
						for (Map.Entry<String, Integer> entry : map.entrySet()) {  
							if(value==entry.getValue()){  
								key=entry.getKey();  
							}  
						}   
						System.out.println(key+"即将到达");	
					}    		
				}//endelse
			}//
	}  
}
