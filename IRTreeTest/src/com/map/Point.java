package com.map;

import java.awt.Color;

public class Point {
	private int id;//目标点id
	private int x;//目标点在坐标中的x索引值 
	private int y;//目标点在坐标中的y索引值 
	private Color color;//颜色 
	private String word;
	private String messager;
	public static int DIAMETER=30;//直径 
	public Point(int id,int x,int y,Color color){ 
		this.id=id;
		this.x=x; 
		this.y=y; 
		this.color=color; 
		this.messager ="PointID="+id+"  横坐标x="+x+"  纵坐标y="+y;
	} 
	 //得到目标点在坐标中的x索引值 
	public int getX(){ 
	  return x; 
	} 
	//得到目标点在坐标中的y索引值 
	public int getY(){ 
	  return y; 
	} 
	 //得到目标点的标注颜色 
	public Color getColor(){ 
	  return color; 
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public static int getDIAMETER() {
		return DIAMETER;
	}
	public static void setDIAMETER(int dIAMETER) {
		DIAMETER = dIAMETER;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public String getMessager() {
		return messager;
	}
	public void setMessager() {
		this.messager=this.messager+"  文本Id及权重:"+word;
	}  
	
	
}
