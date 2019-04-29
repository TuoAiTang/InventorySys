package servlet;

import bean.*;
import dao.Dao;
import util.ut;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.*;


public class Customer  extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("user");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = resp.getWriter();

        int auth = u.getType();
        if(auth != User.CUSTOMER){
            pw.println(ut.AUTH_ERROR);
        }
        else{
            String method = req.getParameter("method");

            if(method.equals("buy")){
                buy(req, resp);
            }

            if(method.equals("order")){
                order(u, req, resp);
            }

            if(method.equals("place_order")){
                placeOrder(u, req, resp);
            }
        }
    }

    private void buy(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Dao d = new Dao();
        //without cast, jsp can not read
        List<Object> objects = d.all("WareHouse");
        List<WareHouse> wareHouses = new ArrayList<>();
        for(Object o : objects)
            wareHouses.add((WareHouse) o);

        //get Pair<name,count>
        //to stores
        List<Object> objects2 = d.all("Store");
        System.out.println(objects2.size());
        List<Pair> stores = new ArrayList<>();
        Set<String> set = new HashSet<>();
        for(Object o : objects2){
            Store s = (Store) o;
            String name = s.getOffline_name();
            if(!set.contains(name)) {
                int count = d.getCount("Store", "offline_name", name);
                stores.add(new Pair(name, count));
                set.add(name);
            }
        }
        req.setAttribute("stores", stores);
        req.setAttribute("warehouses", wareHouses);
        req.getRequestDispatcher("buy.jsp").forward(req, resp);
    }

    private void placeOrder(User u, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Dao d = new Dao();
        String goods_name = req.getParameter("goods_name");
        //获取仓库信息
        List<Object> objects = d.allEq("WareHouse", "goods_name", goods_name);
        int inventAmount = ((WareHouse)objects.get(0)).getAmount();

        //添加订单
        int amount = Integer.parseInt(req.getParameter("amount"));
        if(amount > inventAmount){
            PrintWriter pw = resp.getWriter();
            pw.println(ut.AMOUNT_ERROR);
            return;
        }
        String status = "已付款，待发货";
        String user_name = u.getName();
        String date = ut.getCurrentDate();
        Goods g = (Goods) d.allEq("Goods", "name", goods_name).get(0);
        double money = g.getPrice() * amount;
        CustomerOrder cod = new CustomerOrder(0, goods_name, amount, date, money, status, user_name);
        d.add(cod);

        //添加流水账
        RunningTally rt = new RunningTally(0, 1, money, cod.getId(), date);
        d.add(rt);

        order(u, req, resp);
    }

    private void order(User u, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Dao d = new Dao();
        List<Object> objects = d.allEq("CustomerOrder", "user_name", u.getName());
        List<CustomerOrder> orders = new ArrayList<>();
        //desc order
        for(int i = objects.size() - 1; i >= 0; i--)
            orders.add((CustomerOrder)objects.get(i));

        req.setAttribute("orders", orders);
        req.getRequestDispatcher("customerOrder.jsp").forward(req, resp);
    }
}
