package edu.ncst.web.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;

@WebServlet(name = "AddMatterServlet", urlPatterns = "/AddMatterServlet")
public class AddMatterServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("utf-8"); //设置参数编码格式
        String matter_id=null;
        String ct_id=null;
        String ct_name=req.getParameter("name");
        String matter_time = req.getParameter("date");
        String matter=req.getParameter("matter");
        Integer matter_delete=0;   //添加事项默认事项为0，为待完成状态
        Connection conn = null;
        Statement stat=null;
        PreparedStatement preStat = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/web?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "root", "123456");
            stat = conn.createStatement();
            String preSql = "select max(matter_id) 'matter_id' from t_contact_matter"; //查询表中最大的主键id值
            String preSql1="select ct_id from t_contact where ct_name='"+ct_name+"'"; //根据姓名查找对应id
            System.out.println("");
            rs=stat.executeQuery(preSql);
            if(rs.next()){
                matter_id=rs.getString("matter_id");
                if(matter_id==null){ //如果最大主键id为空，则matter_id设为1
                    matter_id="1";
                }
                else{  //否则设为最大id值加一
                    matter_id=Integer.toString(Integer.parseInt(rs.getString("matter_id"))+1);
                }
            }
            rs=stat.executeQuery(preSql1);
            if(rs.next()){
                ct_id=rs.getString("ct_id");
            }else{
                req.setAttribute("message","该联系人不存在"); //错误信息
                req.setAttribute("path","views/AddMatter.jsp"); //设置弹出错误信息后的跳转路路径，即报错后刷新页面
                RequestDispatcher rd = req.getRequestDispatcher("views/Error.jsp");
                rd.forward(req,resp);
            }
            String sql = "insert into t_contact_matter(ct_id, matter_id, matter_time, matter, matter_delete) values(?,?,?,?,?)";
            preStat = conn.prepareStatement(sql);
            preStat.setString(1,ct_id);
            preStat.setString(2,matter_id);
            preStat.setString(3, matter_time);
            preStat.setString(4, matter);
            preStat.setInt(5, matter_delete);
            preStat.executeUpdate();
            preStat.close();
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
            if (preStat != null)
                preStat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RequestDispatcher rd = req.getRequestDispatcher("MatterServlet"); //请求下一个jsp页面
        rd.forward(req, resp); //servlet处理完了交给下一个jsp处理。
    }
}