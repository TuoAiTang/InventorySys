package servlet;

import bean.User;
import dao.Dao;
import util.ut;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.*;
import bean.*;
public class Offline  extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        User u = (User) req.getSession().getAttribute("user");
        PrintWriter pw = resp.getWriter();
        int auth = u.getType();
        if(auth != User.OFFLINE){
            pw.println(ut.AUTH_ERROR);
            return;
        }

        String method = req.getParameter("method");
        if(method.equals("inventory")){
            invent(req, resp);
        }

        if(method.equals("alloc")){
            alloc(req, resp);
        }

        if(method.equals("progress")){
            progress(req, resp);
        }

        if(method.equals("configure")){
            configure(req, resp);
        }

    }

    private void invent(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        Dao d = new Dao();
        List<Object> list = d.all("Store");
        List<Store> stores = new ArrayList<>();
        for(Object o : list)
            stores.add((Store) o);
        req.setAttribute("stores", stores);
        req.getRequestDispatcher("storeInventory.jsp").forward(req, resp);
    }
    private void alloc(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{

    }
    private void progress(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{

    }
    private void configure(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{

    }
}