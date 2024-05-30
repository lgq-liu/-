package edu.ncst.web.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "DetailCtServlet", urlPatterns = "/DetailCtServlet")
public class DetailCtServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req,resp);
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String imagePath = this.getServletContext().getRealPath("/images");
        String ct_id = req.getParameter("ct_id"); ///获取要查看的联系人的id
        Connection conn=null;
        Statement stat = null;
        ResultSet rs = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); //加载驱动程序
            String url="jdbc:mysql://localhost:3306/web?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "root", "123456");
            stat = conn.createStatement();
            String sql = "select * from t_contact t JOIN t_contact_picture p on t.ct_id=p.ct_id where t.ct_id='"+ct_id+"'";
            rs = stat.executeQuery(sql); //增删改都是executeUpdate,查是Query
            if(rs.next()) {
                req.setAttribute("ct_id",rs.getString("ct_id"));
                req.setAttribute("pic_name",rs.getString("pic_name"));
                req.setAttribute("name",rs.getString("ct_name"));
                req.setAttribute("address",rs.getString("ct_ad"));
                req.setAttribute("email",rs.getString("ct_em"));
                req.setAttribute("QQ",rs.getString("ct_qq"));
                req.setAttribute("weChat",rs.getString("ct_wx"));
                req.setAttribute("postcode",rs.getString("ct_yb"));
                if((rs.getString("ct_mf")).equals("男")){
                    req.setAttribute("sex","0");
                }
                else{
                    req.setAttribute("sex","1");
                }
                req.setAttribute("birth",rs.getString("ct_birth"));
                req.setAttribute("phone",rs.getString("ct_phone"));
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
        RequestDispatcher rd = req.getRequestDispatcher("views/DetailCt.jsp"); //请求下一个jsp页面
        rd.forward(req, resp); //servlet处理完了交给下一个jsp处理。
    }
}