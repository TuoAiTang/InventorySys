package servlet;

import bean.User;
import util.ut;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Supplier  extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        User u = (User) req.getSession().getAttribute("user");
        PrintWriter pw = resp.getWriter();
        int auth = u.getType();
        if(auth != User.SUPPLIER){
            pw.println(ut.AUTH_ERROR);
            return;
        }

        String method = req.getParameter("method");
        if(method.equals("order")){
            order(req, resp);
        }

        if(method.equals("solve")){
            solve(req, resp);
        }
    }

    private void order(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

    }

    private void solve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

    }
}