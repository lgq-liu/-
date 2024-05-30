package edu.ncst.web.DBUtil;

import java.sql.*;

public class DBUtil { //工具类
    public static String getUser(String user){
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        String realPwd = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/web?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "root", "123456");
            stat = conn.createStatement();
            String sql = "select user_password from t_user where user_id='" + user+"'";
            rs = stat.executeQuery(sql); //增删改都是executeUpdate,查是Query
            if (rs.next()) {
                realPwd = rs.getString("user_password");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stat != null)
                stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return realPwd;
    }
    public static Integer getTotalContactPage(String user_id,int pageSize,int ctState){
        int totalCount=0;
        int totalPage=1;
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/web?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "root", "123456");
            stat = conn.createStatement();
            String sql = "select count(*) as totalCount from t_contact where user_id='" + user_id + "'and ct_delete='" + ctState + "'";
            rs = stat.executeQuery(sql); //增删改都是executeUpdate,查是Query
            if (rs.next()) {
                totalCount=rs.getInt("totalCount");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stat != null)
                stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        totalPage=totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
        return totalPage;
    }
    public  static Integer getTotalMatterPage(String user_id,int pageSize,int matter_delete){
        int totalCount=0;  //数据库中总条数
        int totalPage=1;  //定义总页数变量，初始为1
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/web?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "root", "123456");
            stat = conn.createStatement();
            String sql = "SELECT count(*) as totalCount FROM t_contact_matter tcm JOIN t_contact tc ON tcm.ct_id=tc.ct_id where user_id='"
                    +user_id+"' and matter_delete='"+matter_delete+"'";  //事项列表查询语句
            rs = stat.executeQuery(sql); //增删改都是executeUpdate,查是Query
            if (rs.next()) {
                totalCount=rs.getInt("totalCount");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stat != null)
                stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        totalPage=totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
        return totalPage;
    }
    public static String getMaxContactID(){
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        String ct_id = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/web?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "root", "123456");
            stat = conn.createStatement();
            String sql = "select max(ct_id) 'ct_id' from t_contact";
            rs = stat.executeQuery(sql); //增删改都是executeUpdate,查是Query
            if (rs.next()) {
                ct_id=rs.getString("ct_id");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stat != null)
                stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ct_id;
    }
}
