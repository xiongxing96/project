package com.JdbcConn.cn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connector {
	public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");//加载驱动
            System.out.println("\n"+"成功加载sql驱动！");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("找不到sql驱动？");
            e.printStackTrace();
        }
        String url="jdbc:mysql://localhost:3306/521?serverTimezone=UTC"; 
        //mysql连接数据库时提示系统时区出现错误,解决方法：在数据库驱动的url后加上serverTimezone=UTC参数
        Connection conn = null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(url,"root","123456");//获取连接
            System.out.println("成功连接到数据库 521！");
        } catch (SQLException e){
            System.out.println("Fail to connect the database/521!");
            e.printStackTrace();
        }
    }
}
