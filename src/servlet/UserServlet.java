package servlet;

import bean.User;
import dao.Dao;
import util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dao d = new Dao();
        req.setCharacterEncoding("utf-8");
        String name = req.getParameter("name");
        System.out.println(name);
        String password = req.getParameter("password");
        String method = req.getParameter("method");
        List<Object> users = d.allEq("User", "name", name);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = resp.getWriter();

        if(method.equals("login")){
            if(users.size() != 0){
//                Object[] objs = users.get(0);
//                String pwd = objs[1].toString();
//                int t = (int) objs[2];
//                System.out.println(pwd + " " + t);
                User user = (User) users.get(0);
                if(password.equals(user.getPassword())){
                    req.getSession().setAttribute("user", user);
                    req.getRequestDispatcher(
                            "home.jsp").forward(
                            req, resp);
                }else{
                    pw.println(ut.LOGIN_ERROR);
                }
            }
            else{
                pw.println(ut.LOGIN_ERROR);
            }
        }

        if(method.equals("register")){
            if(users.size() != 0){
                pw.println(ut.REGISTER_ERROR);
            }
            else{
                String type = req.getParameter("type");
                int t = ut.getType(type);

                User u = new User(name, password, t);
                d.add(u);
                req.getSession().setAttribute("user", u);
                req.getRequestDispatcher(
                        "home.jsp").forward(
                        req, resp);
            }
        }

    }
}
