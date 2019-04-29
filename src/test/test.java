package test;
import dao.*;
import bean.*;
import servlet.Customer;

import java.util.List;

public class test {
    public static void main(String[] args) {
        Dao d = new Dao();


        List<Object> list = d.allInorder("WareHouse", "goods_name");
        for(Object o : list){
            WareHouse w = (WareHouse) o;
            System.out.println(w.getGoods_name());
        }


    }
}
