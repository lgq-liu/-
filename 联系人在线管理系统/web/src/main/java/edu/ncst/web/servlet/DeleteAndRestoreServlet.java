package edu.ncst.web.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "DeleteAndRestoreServlet", urlPatterns = "/DeleteAndRestoreServlet")
public class DeleteAndRestoreServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String ct_id=req.getParameter("ct_id");
        Connection conn=null;
        Statement stat = null;
        ResultSet rs = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); //加载驱动程序
            String url="jdbc:mysql://localhost:3306/web?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "root", "123456");
            stat = conn.createStatement();
            String sql = "SELECT ct_delete FROM t_contact where ct_id='"+ct_id+"'";
            String sql1="";
            rs = stat.executeQuery(sql); //执行查询操作
            if(rs.next()) {
                if(rs.getString("ct_delete").equals("1")){
                    sql1="UPDATE t_contact SET ct_delete='0' where ct_id='"+ct_id+"'";
                }else{
                    sql1="UPDATE t_contact SET ct_delete='1' where ct_id='"+ct_id+"'";
                }
                stat.executeUpdate(sql1);
                stat.close();
            }
        }

        catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        catch(SQLException e){
            e.printStackTrace();
        }
        try {
            if (rs != null)
                rs.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stat != null)
                stat.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        RequestDispatcher rd = req.getRequestDispatcher("ContactListServlet"); //请求下一个jsp页面
        rd.forward(req, resp); //servlet处理完了交给下一个jsp处理。
    }
}