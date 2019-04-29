package test;
import dao.*;
import bean.*;
import servlet.Customer;

import java.util.List;

public class test {
    public static void main(String[] args) {
        Dao d = new Dao();
//        WareHouse wh = new WareHouse("Strawberry", 1200, 2);
//        d.add(wh);
//
//        WareHouse wh2 = new WareHouse("Apple", 0, 0);
//        d.update(wh2);

        List<Object> lists = d.orEq("CustomerOrder", "status", "已付款，待发货",
                "已付款，待补货", "已付款，正在补货", "已发货，待签收");
        for(Object o : lists){
            CustomerOrder cod = (CustomerOrder) o;
            System.out.println(cod.getId());
        }
    }
}
