package com.java1996;

import java.util.HashMap;

/**
 * UserPoint即用户用以查询的候选目标点
 * @author 17699_000
 *
 */
public class UserPoint extends Point {
	private int id;
	private HashMap<Integer,Float>textMap=new HashMap<Integer,Float>();
	public UserPoint(int id,float longitude,float latitude) {
		super(longitude, latitude);
		this.id=id;
}
	public int getId() {
		return id;
	}
	public void settextMap(String s) {
		String[] str=s.split(",");
		for(int i=0;i<str.length;i=i+2)
		{
			textMap.put(Integer.valueOf(str[i]), Float.valueOf(str[i+1]));
		}
	}
	public HashMap<Integer, Float> gettextMap(){
		return textMap;
	}
}
