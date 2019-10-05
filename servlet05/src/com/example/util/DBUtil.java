package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
	private final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String URL = "jdbc:mysql://localhost:3306/521?serverTimezone=UTC&useSSL=false"; 
	private final String USER = "root";
	private final String PASSWORD = "123456";
	
	private static DBUtil db = null;
	//私有构造器
	private DBUtil () {
		
	}
	//共有的返回该对象的静态方法
	public static DBUtil getInstance() {
		if(db == null) {
			db = new DBUtil();
		}
		return db;
	}
	
	public Connection getCon() {
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL,USER,PASSWORD);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
