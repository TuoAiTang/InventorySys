package servlet;

import bean.RunningTally;
import bean.User;
import dao.Dao;
import util.ut;

import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Accountant  extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("user");
        int auth = u.getType();
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = resp.getWriter();
        if(auth != User.ACCOUNTANT){
            pw.println(ut.AUTH_ERROR);
            return;
        }
        Dao d = new Dao();
        String method = req.getParameter("method");
        if(method.equals("tally")){
            tally(req, resp);
        }

    }

    private void tally(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dao d = new Dao();
        List<Object> objectList = d.all("RunningTally");
        List<RunningTally> tallyList = new ArrayList<>();
        for(int i = objectList.size() - 1; i >= 0; i--)
            tallyList.add((RunningTally) objectList.get(i));
        req.setAttribute("tally", tallyList);
        req.getRequestDispatcher("tally.jsp").forward(req, resp);
    }
}
