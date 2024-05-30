package edu.ncst.web.servlet;
import edu.ncst.web.DBUtil.DBUtil;
import edu.ncst.web.entity.Matter;

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

@WebServlet(name = "MatterServlet", urlPatterns = "/MatterServlet")
public class MatterServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req,resp);
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Matter> matterList = new ArrayList<>();
        int matter_delete=(req.getParameter("matter_delete")!=null) ? Integer.parseInt(req.getParameter("matter_delete")):0; //获取要查询的状态
        int pageSize=5;
        int currentPage = (req.getParameter("currentPage") != null) ? Integer.parseInt(req.getParameter("currentPage")) : 1;
        int totalMatterPage = DBUtil.getTotalMatterPage("10001", pageSize, matter_delete); //获取总页数
        int start = (currentPage - 1) * pageSize;  //查询起始位置
        req.setAttribute("currentPage",currentPage);
        req.setAttribute("totalMatterPage",totalMatterPage);
        String keyword = req.getParameter("keyword");
        String sql=null;
        Connection conn=null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        Boolean fail = false;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); //加载驱动程序
            String url="jdbc:mysql://localhost:3306/web?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "root", "123456");
            if(req.getParameter("sort")!=null){
                String sort = req.getParameter("sort");
                if((keyword!=null)&&(keyword!="")){  //判断是否有查找操作，有执行按事项查找
                    sql = "SELECT * FROM t_contact_matter tcm JOIN t_contact tc ON tcm.ct_id=tc.ct_id WHERE matter_delete='"
                            +matter_delete+"' AND matter='"+keyword+"'  ORDER BY matter_time "+sort+" LIMIT ?,?";
                }
                else{
                    sql = "SELECT * FROM t_contact_matter tcm JOIN t_contact tc ON tcm.ct_id=tc.ct_id WHERE matter_delete='"
                            +matter_delete+"' ORDER BY matter_time "+sort+" LIMIT ?,?";
                }
            }
            else{
                if((keyword!=null)&&(keyword!="")) {  //判断是否有查找操作
                    sql = "SELECT * FROM t_contact_matter tcm JOIN t_contact tc ON tcm.ct_id=tc.ct_id WHERE matter_delete='"
                            +matter_delete+"' AND matter='"+keyword+"' LIMIT ?,?";
                }
                else{
                    sql = "SELECT * FROM t_contact_matter tcm JOIN t_contact tc ON tcm.ct_id=tc.ct_id WHERE matter_delete='"
                            +matter_delete+"' LIMIT ?,?";

                }
            }
            stat = conn.prepareStatement(sql);
            stat.setInt(1, start);   //查询的起始位置
            stat.setInt(2, pageSize);  //查询的数量
            rs = stat.executeQuery(); //执行查询操作，每次查询pageSize页
            while(rs.next()) {
                Matter matter = new Matter();
                matter.setCt_name(rs.getString("ct_name"));
                matter.setCt_id(rs.getString("ct_id"));
                matter.setMatter_id(rs.getString("matter_id"));
                matter.setMatter_time(rs.getString("matter_time").replace("00:00:00", ""));
                matter.setMatter(rs.getString("matter"));
                matter.setMatter_delete(rs.getInt("matter_delete"));
                matterList.add(matter);
            }
            if(matterList.size()==0&&keyword!=null&&keyword!=""){ //如果查询没找到
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
            req.setAttribute("matterList",matterList);
            req.setAttribute("selectedItem",matter_delete);
            RequestDispatcher rd = req.getRequestDispatcher("views/Matter.jsp"); //请求下一个jsp页面
            rd.forward(req, resp); //servlet处理完了交给下一个jsp处理。
    }
}