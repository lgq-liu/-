package edu.ncst.web.servlet;

import edu.ncst.web.DBUtil.DBUtil;
import edu.ncst.web.entity.Contact;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ContactListServlet", urlPatterns = "/ContactListServlet")
public class ContactListServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req,resp);
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Contact> contactList = new ArrayList<>();
        int pageSize=5;
        int currentPage = (req.getParameter("currentPage") != null) ? Integer.parseInt(req.getParameter("currentPage")) : 1;
        int totalContactPage = DBUtil.getTotalContactPage("10001",pageSize,0); //获取总页数
        int start = (currentPage - 1) * pageSize; //起始查询位置
        req.setAttribute("currentPage",currentPage);
        req.setAttribute("totalContactPage",totalContactPage);
        String keyword=((req.getParameter("keyword"))!=null) ? req.getParameter("keyword") : "";
        String sql=null;
        Connection conn=null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        boolean fail=false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); //加载驱动程序
            String url="jdbc:mysql://localhost:3306/web?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "root", "123456");
            if(req.getParameter("sort")!=null){ //判断有无排序请求
                String sort = req.getParameter("sort");
                String sex=req.getParameter("sex");
                req.setAttribute("selectedItem",sex);  //先存起来以便向前端交互
                if(!(sex.equals("all"))){   //判断有无性别筛选
                    if(sex.equals("0")){
                        sex = "男";
                    }
                    else{
                        sex="女";
                    }
                    sql="SELECT ct_id,ct_name, ct_mf, ct_phone FROM t_contact  WHERE ct_delete=0 AND ct_mf='"+sex+ "' ORDER BY ct_name "+sort+ " LIMIT ?,?";
                }
                else{
                    sql="SELECT ct_id,ct_name, ct_mf, ct_phone FROM t_contact  WHERE ct_delete=0 ORDER BY ct_name "+sort+ " LIMIT ?,?";
                }
            }
            else{
                String sex=((req.getParameter("sex"))!=null)?req.getParameter("sex"):"all"; //判断参数是否为空
                req.setAttribute("selectedItem",sex);  //先存起来以便向前端交互
                if(!(sex.equals("all"))) {   //判断有无性别筛选
                    if (sex.equals("0")) {
                        sex = "男";
                    } else {
                        sex = "女";
                    }
                    sql = "SELECT ct_id,ct_name, ct_mf, ct_phone FROM t_contact where ct_delete=0 AND ct_mf='"+sex+"' LIMIT ?,?";
                }
                else{
                    sql = "SELECT ct_id,ct_name, ct_mf, ct_phone FROM t_contact where ct_delete=0 LIMIT ?,?";
                }
            }
            if((keyword!=null)&&(keyword!="")){  //判断是否有搜索操作
                sql="SELECT ct_id,ct_name, ct_mf, ct_phone FROM t_contact where ct_delete=0 AND ct_name='"+keyword+"' LIMIT ?,?";
            }
            stat = conn.prepareStatement(sql);
            stat.setInt(1, start);
            stat.setInt(2, pageSize);
            rs = stat.executeQuery(); //执行查询操作，每次查询pageSize页
            while(rs.next()) {
                Contact contact = new Contact();
                contact.setCt_id(rs.getString("ct_id"));
                contact.setCt_name(rs.getString("ct_name"));
                contact.setCt_mf(rs.getString("ct_mf"));
                contact.setCt_phone(rs.getString("ct_phone"));
                contactList.add(contact);
            }
            if(contactList.size()==0&&keyword!=null&&keyword!=""){ //如果查询没找到
                fail=true;
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
        if(fail){
            req.setAttribute("message","未找到指定内容");
            req.setAttribute("path","ContactListServlet");
            RequestDispatcher rd = req.getRequestDispatcher("views/Error.jsp");
            rd.forward(req, resp);
        }
        else{
            req.setAttribute("contactList",contactList);
            RequestDispatcher rd = req.getRequestDispatcher("views/ContactList.jsp"); //请求下一个jsp页面
            rd.forward(req, resp); //servlet处理完了交给下一个jsp处理。
        }
    }
}
