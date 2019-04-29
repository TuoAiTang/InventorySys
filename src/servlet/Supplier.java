package servlet;

import bean.PurchaseOrder;
import bean.*;
import dao.Dao;
import util.ut;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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
        Dao d = new Dao();
        List<Object> list = d.allEq("PurchaseOrder", "status", "已付款，待发货");
        List<PurchaseOrder> orders = new ArrayList<>();
        for(int i = list.size() - 1; i >= 0; i--)
            orders.add((PurchaseOrder) list.get(i));

        req.setAttribute("orders", orders);
        req.getRequestDispatcher("purchaseOrderSolve.jsp").forward(req, resp);
    }

    private void solve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Dao d = new Dao();
        int id = Integer.parseInt(req.getParameter("order_id"));
        PurchaseOrder pod = (PurchaseOrder)d.get(PurchaseOrder.class, id);
        int amount = pod.getAmount();
        String goods_name = pod.getGoods_name();
        List<Object> objects = d.allEq("Supp", "goods_name", goods_name);
        Supp supp = (Supp)objects.get(0);
        int inventAmount = supp.getAmount();
        PrintWriter pw = resp.getWriter();
        if(inventAmount >= amount){
            //更新采购单，减少库存
            pod.setStatus("已发货，待签收");
            d.update(pod);
            supp.setAmount(inventAmount - amount);
            d.update(supp);
            pw.println(ut.SOLVE_SUCCESS);
            return;
        }
        else{
            pw.println(ut.AMOUNT_ERROR);
            return;
        }

    }
}