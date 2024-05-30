package edu.ncst.web.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
@WebServlet(name = "MatterStatServlet", urlPatterns = "/MatterStatServlet")
public class MatterStatServlet extends HttpServlet {  //联系人事项列表中的按钮操作，包括取消和完成
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String matter_id=req.getParameter("matter_id");
        String matter_delete=req.getParameter("matter_delete");  //记录更新前的下拉框选中状态，方便跳转回页面时合理显示
        Integer newMatter_delete = ((req.getParameter("operate")).equals("cancel")) ? 1 : 2; //取消为1，完成为2
        Connection conn=null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); //加载驱动程序
            String url="jdbc:mysql://localhost:3306/web?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "root", "123456");
            String sql="UPDATE t_contact_matter SET matter_delete=? where matter_id='"+matter_id+"'";
            stat = conn.prepareStatement(sql); //装入sql语句
            stat.setInt(1, newMatter_delete);
            stat.executeUpdate();  //执行更新语句
            stat.close();
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
        RequestDispatcher rd = req.getRequestDispatcher("MatterServlet?matter_delete="+matter_delete); //请求下一个jsp页面
        rd.forward(req, resp); //servlet处理完了交给下一个jsp处理。
    }
}
