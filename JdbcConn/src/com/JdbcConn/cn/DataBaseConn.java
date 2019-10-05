package com.JdbcConn.cn;

import java.sql.*;

public class DataBaseConn {
	public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");//加载驱动
            System.out.println("\n"+"成功加载sql驱动！");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("找不到sql驱动？");
            e.printStackTrace();
        }
        String url="jdbc:mysql://localhost:3306/521?serverTimezone=UTC&useSSL=false"; 
        //mysql连接数据库时提示系统时区出现错误,解决方法：在数据库驱动的url后加上serverTimezone=UTC参数
        Connection conn = null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(url,"root","123456");//获取连接
            System.out.println("成功连接到数据库 521！");
            String sql = "select * from emp";//创建sql命令
            ps = conn.prepareStatement(sql); //创建sql命令对象
            rs = ps.executeQuery();//执行sql
            //emp 为你表的名称
            System.out.println(rs.toString());
            while (rs.next()) {//遍历结果
                System.out.println(rs.getString("name"));
            }
            ps.close();
            conn.close();//关闭连接
        } catch (SQLException e){
            System.out.println("Fail to connect the database/521!");
            e.printStackTrace();
        }
    }
}
