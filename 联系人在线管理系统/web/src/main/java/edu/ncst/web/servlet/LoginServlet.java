package edu.ncst.web.servlet;

import edu.ncst.web.DBUtil.DBUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String user = req.getParameter("user");
        String pwd = req.getParameter("password");
        String realPwd = DBUtil.getUser(user);
        boolean succ = false;
        if(realPwd!=null&&realPwd.equals(pwd)){
            succ=true;
        }
        if (succ) {
            RequestDispatcher rd = req.getRequestDispatcher("/ContactListServlet");
            rd.forward(req, resp);
        } else {
            req.setAttribute("message","登陆失败");
            req.setAttribute("path","views/Login.jsp");
            RequestDispatcher rd = req.getRequestDispatcher("views/Error.jsp");
            rd.forward(req, resp);
        }
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req,resp);
    }
}

