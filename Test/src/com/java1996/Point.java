package com.java1996;

import java.awt.FlowLayout;

/**
 * 父类
 * @author 17699_000
 *
 */
public class Point {
	private float longitude;//经度
	private float latitude;//纬度
	public Point(float longitude,float latitude) {
		this.longitude=longitude;
		this.latitude=latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
}
