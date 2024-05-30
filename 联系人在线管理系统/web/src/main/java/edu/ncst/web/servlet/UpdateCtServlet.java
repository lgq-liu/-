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

@WebServlet(name = "UpdateCtServlet", urlPatterns = "/UpdateCtServlet")
public class UpdateCtServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String ct_id=null;
        String filename=null;
        String user_id="10001";
        String name = null;
        String address =null;
        String postcode = null;
        String qq = null;
        String weChat = null;
        String email = null;
        String sex = null;
        String birth = null;
        String phone = null;
        // 设置上传图片的保存路径
        String savePath = this.getServletContext().getRealPath("/images");
        File file = new File(savePath);
        // 判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath + "目录不存在，需要创建");
            // 创建目录
            file.mkdir();
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 2、创建一个文件上传解析器
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        // 3、判断提交上来的数据是否是上传表单的数据
        if (!ServletFileUpload.isMultipartContent(req)) {
            // 按照传统方式获取数据
            return;
        }
        try {
            List<FileItem> list = upload.parseRequest(req);
            for (FileItem item : list) {
                if(item.isFormField()){
                    String parameter=item.getFieldName();
                    String value=item.getString("utf-8");
                    if(parameter.equals("update")){
                        ct_id=value;
                    }
                    if(parameter.equals("name")){
                        name=value;
                    }
                    if(parameter.equals("address")){
                        address=value;
                    }
                    if(parameter.equals("postcode")){
                        postcode=value;
                    }
                    if(parameter.equals("QQ")){
                        qq=value;
                    }
                    if(parameter.equals("weChat")){
                        weChat=value;
                    }
                    if(parameter.equals("e-mail")){
                        email=value;
                    }
                    if(parameter.equals("sex")){
                        if(value.equals("0")){
                            sex="男";
                        }else{
                            sex="女";
                        }
                    }
                    if(parameter.equals("birth")){
                        birth=value;
                    }
                    if(parameter.equals("phone")){
                        phone=value;
                    }
                }
                else{
                    filename = item.getName();// 获得一个项的文件名称
                    if (filename == null || filename.trim().equals("")) {// 如果为空则跳过
                        continue;
                    }

                    filename = filename.substring(filename.lastIndexOf("\\") + 1);
                    if (filename.substring(filename.lastIndexOf(".") + 1).equals("png")
                            || filename.substring(filename.lastIndexOf(".") + 1).equals("jpg")
                            || filename.substring(filename.lastIndexOf(".") + 1).equals("jpeg")) {
                        InputStream in = item.getInputStream();// 獲得上傳的輸入流
                        FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);// 指定web-inf目錄下的images文件
                        req.setAttribute("path",  "images"+"\\" + filename);
                        System.out.println("savePath"+"\\"+filename);
                        int len = 0;
                        byte buffer[] = new byte[1024];
                        while ((len = in.read(buffer)) > 0)// 每次讀取
                        {
                            out.write(buffer, 0, len);
                        }
                        in.close();
                        out.close();
                        item.delete();
                    } else {  //必须是图片才能上传否则失败
                        return ;
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        Statement stat=null;
        PreparedStatement preStat = null;
        PreparedStatement st=null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/web?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "root", "123456");
            stat = conn.createStatement();

            String sql = "update t_contact set ct_name=?,ct_ad=?,ct_yb=?,ct_qq=?,ct_wx=?,ct_em=?,ct_mf=?,ct_birth=?," +
                    "ct_phone=? where ct_id=?";
            preStat = conn.prepareStatement(sql);
            preStat.setString(1, name);
            preStat.setString(2, address);
            preStat.setString(3, postcode);
            preStat.setString(4, qq);
            preStat.setString(5, weChat);
            preStat.setString(6, email);
            preStat.setString(7, sex);
            preStat.setString(8, birth);
            preStat.setString(9, phone);
            preStat.setString(10, ct_id);
            preStat.executeUpdate();
            String pic_id = ct_id; //设置图片id为联系人id
            String sql1="update t_contact_picture set pic_name=? where pic_id=?";
            st = conn.prepareStatement(sql1);
            st.setString(1, filename);
            st.setString(2, pic_id);
            st.executeUpdate();
            st.close();
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
            if (st != null)
                st.close();
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
        RequestDispatcher rd = req.getRequestDispatcher("ContactListServlet"); //请求下一个jsp页面
        rd.forward(req, resp); //servlet处理完了交给下一个jsp处理。
    }

}