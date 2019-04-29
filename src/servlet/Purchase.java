package servlet;

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

public class Purchase  extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("user");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = resp.getWriter();
        Dao d = new Dao();
        int auth = u.getType();
        if(auth != User.PURCHASE){
            pw.println(ut.AUTH_ERROR);
        }
        else{
            String method = req.getParameter("method");
            //采购页面
            if(method.equals("purchase")){
                buy(req, resp);
            }
            //查看订单
            if(method.equals("order")){
                order(req, resp);
            }
            //下单页面
            if(method.equals("place_order")){
                placeOrder(req, resp);
            }
            //确认收货，增加库存
            if(method.equals("sign_for")){
                signFor(req, resp);
            }

            if(method.equals("alloc")){
                alloc(req, resp);
            }

            if(method.equals("progress")){
                progress(req, resp);
            }

            //跳转-->处理采购
            if(method.equals("purchase_solve")){
                purchaseSolve(req, resp);
            }

            //跳转-->处理调拨
            if(method.equals("alloc_solve")){
                allocSolve(req, resp);
            }
        }
    }

    private void buy(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Dao d = new Dao();
        //without cast, jsp can not read
        List<Object> objects = d.all("Supp");
        List<Supp> supp_list = new ArrayList<>();
        for(Object o : objects)
            supp_list.add((Supp) o);


        req.setAttribute("supp_list", supp_list);
        req.getRequestDispatcher("purchase.jsp").forward(req, resp);
    }

    private void placeOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Dao d = new Dao();
        String goods_name = req.getParameter("goods_name");
        //获取仓库信息
        List<Object> objects = d.allEq("Supp", "goods_name", goods_name);
        int inventAmount = ((Supp)objects.get(0)).getAmount();

        //添加订单
        int amount = Integer.parseInt(req.getParameter("amount"));
        if(amount > inventAmount){
            PrintWriter pw = resp.getWriter();
            pw.println(ut.AMOUNT_ERROR);
            return;
        }
        String status = "已付款，待发货";
        String date = ut.getCurrentDate();
        Goods g = (Goods) d.allEq("Goods", "name", goods_name).get(0);
        double money = g.getPrice() * amount;
        PurchaseOrder pod = new PurchaseOrder(0, goods_name, amount, date, money, status);
        d.add(pod);

        //添加流水账
        RunningTally rt = new RunningTally(0, 0, money, pod.getId(), date);
        d.add(rt);

        order(req, resp);
    }

    private void order(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Dao d = new Dao();
        List<Object> objects = d.all("PurchaseOrder");
        List<PurchaseOrder> orders = new ArrayList<>();
        //desc order
        for(int i = objects.size() - 1; i >= 0; i--)
            orders.add((PurchaseOrder)objects.get(i));

        req.setAttribute("orders", orders);
        req.getRequestDispatcher("purchaseOrder.jsp").forward(req, resp);
    }

    private void signFor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Dao d = new Dao();
        int id = Integer.parseInt(req.getParameter("order_id"));
        PurchaseOrder pod = (PurchaseOrder) d.get(PurchaseOrder.class, id);

        //增加库存
        List<Object> objects2 = d.allEq("WareHouse", "goods_name", pod.getGoods_name());
        int inventAmount = ((WareHouse)objects2.get(0)).getAmount();
        WareHouse wh = (WareHouse)d.allEq("WareHouse", "goods_name", pod.getGoods_name()).get(0);
        wh.setAmount(inventAmount + pod.getAmount());
        d.update(wh);

        //更新订单状态
        pod.setStatus("已签收");
        d.update(pod);

        order(req, resp);
    }

    private void alloc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Dao d = new Dao();
        int id = Integer.parseInt(req.getParameter("ar_id"));
        AllocRequest ar = (AllocRequest) d.get(AllocRequest.class, id);

        //减少库存
        List<Object> objects2 = d.allEq("WareHouse", "goods_name", ar.getGoods_name());
        int inventAmount = ((WareHouse)objects2.get(0)).getAmount();
        WareHouse wh = (WareHouse)d.allEq("WareHouse", "goods_name", ar.getGoods_name()).get(0);
        if(inventAmount < ar.getAmount()){
            allocSolve(req, resp);
            return;
        }

        wh.setAmount(inventAmount - ar.getAmount());
        d.update(wh);

        //更新订单状态
        ar.setStatus("已调拨，待收货");
        d.update(ar);

        allocSolve(req, resp);
    }

    private void allocSolve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Dao d = new Dao();
        //已付款订单，升序，先来先发货
        List<Object> objects = d.orEq("AllocRequest", "status", "加入发货队列", "申请中，等待补货");
        List<AllocRequest> readyOrder = new ArrayList<>();
        List<AllocRequest> waitOrder = new ArrayList<>();
        for(Object o : objects){
            AllocRequest ar = (AllocRequest)o;
            //获取仓库信息
            List<Object> objects2 = d.allEq("WareHouse", "goods_name", ar.getGoods_name());
            int inventAmount = ((WareHouse)objects2.get(0)).getAmount();
            if(ar.getAmount() <= inventAmount){
                readyOrder.add(ar);
                ar.setStatus("加入发货队列");
            }
            else{
                ar.setStatus("申请中，等待补货");
                d.update(ar);
                waitOrder.add(ar);
            }
        }

        List<Object> objects1 = d.allEq("AllocRequest", "status", "已调拨，待收货");
        for(int i = objects1.size() - 1; i >= 0; i--)
            readyOrder.add((AllocRequest)objects1.get(i));
        req.setAttribute("readyOrder", readyOrder);
        req.setAttribute("waitOrder", waitOrder);
        req.getRequestDispatcher("allocSolve.jsp").forward(req, resp);
    }

    private void purchaseSolve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Dao d = new Dao();
        List<Object> list = d.allEq("PurchaseOrder", "status", "已发货");
        List<PurchaseOrder> orders = new ArrayList<>();
        for(Object o : list)
            orders.add((PurchaseOrder) o);
        if(orders.size() == 0)
            req.setAttribute("orders", null);
        else
            req.setAttribute("orders", orders);
        req.getRequestDispatcher("purchaseSolve.jsp").forward(req, resp);
    }

    private void progress(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Dao d = new Dao();
        String ar_id = req.getParameter("ar_id");
        AllocRequest ar = (AllocRequest) d.allEq("AllocRequest", "id", ar_id).get(0);
        String goods_name = ar.getGoods_name();
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
