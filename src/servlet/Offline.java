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
            invent(u, req, resp);
        }

        if(method.equals("submit")){
            submit(u, req, resp);
        }

        if(method.equals("request")){
            request(u, req, resp);
        }

        if(method.equals("alloc")){
            alloc(req, resp);
        }

        if(method.equals("progress")){
            progress(u, req, resp);
        }

        if(method.equals("configure")){
            configure(req, resp);
        }

        if(method.equals("modify")){
            modify(u, req, resp);
        }
    }

    private void invent(User u, HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        Dao d = new Dao();
        List<Object> list = d.allEq("Store", "offline_name", u.getName());
        List<Store> stores = new ArrayList<>();
        for(int i = list.size() - 1; i >= 0; i--)
            stores.add((Store) list.get(i));
        req.setAttribute("stores", stores);
        req.getRequestDispatcher("storeInventory.jsp").forward(req, resp);
    }

    private void alloc(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        Dao d = new Dao();
        //without cast, jsp can not read
        List<Object> objects = d.allInorder("WareHouse", "goods_name");
        List<WareHouse> wareHouses = new ArrayList<>();
        for(Object o : objects)
            wareHouses.add((WareHouse) o);
        req.setAttribute("warehouses", wareHouses);
        req.getRequestDispatcher("alloc.jsp").forward(req, resp);
    }

    private void progress(User u, HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        request(u, req, resp);
    }

    private void configure(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        Dao d = new Dao();
        //without cast, jsp can not read
        List<Object> objects = d.allInorder("WareHouse", "goods_name");
        List<WareHouse> wareHouses = new ArrayList<>();
        for(Object o : objects)
            wareHouses.add((WareHouse) o);
        req.setAttribute("warehouses", wareHouses);
        req.getRequestDispatcher("configure.jsp").forward(req, resp);
    }

    private void request(User u, HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        Dao d = new Dao();
        List<Object> list = d.allEq("AllocRequest", "offline_name", u.getName());
        List<AllocRequest> ars = new ArrayList<>();
        for(int i = list.size() - 1; i >= 0; i--)
            ars.add((AllocRequest) list.get(i));
        req.setAttribute("ars", ars);
        req.getRequestDispatcher("request.jsp").forward(req, resp);
    }

    private void submit(User u, HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        Dao d = new Dao();
        String goods_name = req.getParameter("goods_name");
        int amount = Integer.parseInt(req.getParameter("amount"));
        int inventAmount = ((WareHouse)d.allEq("WareHouse", "goods_name", goods_name).get(0)).getAmount();
        if(amount > inventAmount){
            PrintWriter pw = resp.getWriter();
            pw.println(ut.AMOUNT_ERROR);
            return;
        }

        String offline_name = u.getName();
        String date = ut.getCurrentDate();
        String status = "加入发货队列";
        AllocRequest ar = new AllocRequest(0, offline_name, goods_name, amount, date, status);
        d.add(ar);

        request(u, req, resp);
    }

    private void modify(User u, HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        Dao d = new Dao();
        PrintWriter pw = resp.getWriter();
        String type = req.getParameter("type");
        //出库
        if(type.equals("out")){
            String goods_name = req.getParameter("goods_name");
            int amount = Integer.parseInt(req.getParameter("amount"));
            //存在出库大于库存风险
            List<Object> stores = d.allEq("Store", "offline_name", u.getName());
            Store store = null;
            for(Object o : stores){
                Store s = (Store) o;
                if(s.getGoods_name().equals(goods_name)){
                    store = s;
                    break;
                }
            }
            //该店没有该商品
            if(store == null){
                pw.println(ut.GOODS_NAME_ERROR);
                return;
            }
            //该店商品数量不足，无法出库
            if(amount > store.getAmount()){
                pw.println(ut.AMOUNT_ERROR);
                return;
            }

            store.setAmount(store.getAmount() - amount);
            d.update(store);
            invent(u, req, resp);
        }
        //入库
        //实际上只需要ar_id这一个参数
        else if(type.equals("in")){
            int id = Integer.parseInt(req.getParameter("ar_id"));
            //存在订单不存在,或者订单目前不是待收货的状态
            Object o = d.get(AllocRequest.class, id);
            if(o == null || !((AllocRequest)o).getStatus().equals("已调拨，待收货")){
                pw.println(ut.ORDER_ERROR);
                return;
            }

            AllocRequest ar = (AllocRequest) o;
            List<Object> stores = d.allEq("Store", "offline_name", u.getName());
            Store store = null;
            for(Object obj : stores){
                Store s = (Store) obj;
                if(s.getGoods_name().equals(ar.getGoods_name())){
                    store = s;
                    break;
                }
            }
            //该店本没有该商品,创建记录
            if(store == null){
                store = new Store(0, ar.getGoods_name(), ar.getAmount(), u.getName());
                d.add(store);
            }
            //如果有，就更新数量
            else{
                store.setAmount(store.getAmount() + ar.getAmount());
                d.update(store);
            }
            ar.setStatus("已签收");
            d.update(ar);
            request(u, req, resp);
        }
    }
}