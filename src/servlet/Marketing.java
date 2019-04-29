package servlet;

import bean.CustomerOrder;
import bean.PurchaseOrder;
import bean.User;
import bean.WareHouse;
import dao.Dao;
import util.ut;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.*;
public class Marketing  extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        User u = (User) req.getSession().getAttribute("user");
        PrintWriter pw = resp.getWriter();


        int auth = u.getType();
        if(auth != User.MARKETING){
            pw.println(ut.AUTH_ERROR);
        }else{
            String method = req.getParameter("method");
            if(method == null){
                order(req, resp);
            }
            else if(method.equals("solve")){
                solve(req, resp);
            }
            else if(method.equals("progress")){
                progress(req, resp);
            }
        }
    }

    private void order(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dao d = new Dao();

        //已付款订单，升序，先来先发货
        List<Object> objects = d.orEq("CustomerOrder", "status", "已付款，待发货", "已付款，正在补货");
        List<CustomerOrder> readyOrder = new ArrayList<>();
        List<CustomerOrder> waitOrder = new ArrayList<>();
        for(Object o : objects){
            CustomerOrder cod = (CustomerOrder)o;
            //获取仓库信息
            List<Object> objects2 = d.allEq("WareHouse", "goods_name", cod.getGoods_name());
            int inventAmount = ((WareHouse)objects2.get(0)).getAmount();
            if(cod.getAmount() <= inventAmount){
                readyOrder.add(cod);
                cod.setStatus("已付款，待发货");
            }
            else{
                cod.setStatus("已付款，正在补货");
                d.update(cod);
                waitOrder.add(cod);
            }
        }

        List<Object> objects1 = d.allEq("CustomerOrder", "status", "已发货，待签收");
        for(int i = objects1.size() - 1; i >= 0; i--)
            readyOrder.add((CustomerOrder)objects1.get(i));
        req.setAttribute("readyOrder", readyOrder);
        req.setAttribute("waitOrder", waitOrder);
        req.getRequestDispatcher("marketOrder.jsp").forward(req, resp);
    }

    private void solve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Dao d = new Dao();
        int id = Integer.parseInt(req.getParameter("cod_id"));
        CustomerOrder cod = (CustomerOrder) d.get(CustomerOrder.class, id);

        //减少库存
        List<Object> objects2 = d.allEq("WareHouse", "goods_name", cod.getGoods_name());
        int inventAmount = ((WareHouse)objects2.get(0)).getAmount();
        WareHouse wh = (WareHouse)d.allEq("WareHouse", "goods_name", cod.getGoods_name()).get(0);
        if(inventAmount < cod.getAmount()){
            order(req, resp);
            return;
        }
        wh.setAmount(inventAmount - cod.getAmount());
        d.update(wh);

        //更新订单状态
        cod.setStatus("已发货，待签收");
        d.update(cod);

        //提示并跳转到查看订货

        order(req, resp);
    }

    private void progress(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Dao d = new Dao();
        String cod_id = req.getParameter("cod_id");
        CustomerOrder cod = (CustomerOrder) d.allEq("CustomerOrder", "id", cod_id).get(0);
        String goods_name = cod.getGoods_name();
        List<Object> list = d.allEq("PurchaseOrder", "goods_name", goods_name);
        List<PurchaseOrder> purchaseOrders = new ArrayList<>();
        for(int i = list.size() - 1; i >= 0; i--)
            purchaseOrders.add((PurchaseOrder)list.get(i));
        if(purchaseOrders.size() == 0)
            req.setAttribute("purchaseOrders", null);
        else
            req.setAttribute("purchaseOrders", purchaseOrders);
        req.getRequestDispatcher("supplement_progress.jsp").forward(req, resp);
    }
}
