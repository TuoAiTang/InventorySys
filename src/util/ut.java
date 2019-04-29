package util;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
public class ut {
    public static String AUTH_ERROR = "<div style='color:red'>权限不匹配</div>\n" +
            "<a href=index.jsp style='color:blue'>重新登陆</a>";
    public static String LOGIN_ERROR = "<div style='color:red'>账号或密码错误</div>\n" +
            "<a href=index.jsp style='color:blue'>重新登陆</a>";
    public static String REGISTER_ERROR = "<div style='color:red'>账号已经存在</div>\n" +
            "<a href=index.jsp style='color:blue'>登陆</a>";
    public static String AMOUNT_ERROR = "<div style='color:red'>失败：输入的数量超过了库存</div>\n" +
            "<a href=# style='color:blue'>返回</a>";
    public static String GOODS_NAME_ERROR = "<div style='color:red'>失败：输入的商品名称有误</div>\n" +
            "<a href=# style='color:blue'>返回</a>";
    public static String SOLVE_SUCCESS = "<div style='color:red'>发货成功</div>\n" +
            "<div style='color:blue'>正在返回...</div>";
    public static String ORDER_ERROR = "<div style='color:red'>订单不存在</div>\n" +
            "<div style='color:blue'>返回...</div>";
    public static int getType(String type){
        int t = 0;
        switch (type){
            case "customer":
                t = 0;
                break;
            case "accountant":
                t = 2;
                break;
            case "purchase":
                t = 3;
                break;
            case "marketing":
                t = 1;
                break;
            case "manager":
                t = 6;
                break;
            case "supplier":
                t = 4;
                break;
            case "offline":
                t = 5;
                break;
            default:
                t = 0;
                break;
        }

        return t;
    }
    public static int getSales(){
        Random r = new Random();
        return r.nextInt(2000);
    }

    public static String getDistance(){
        Random r = new Random();
        double number = 1 + r.nextInt(20) + ((int) (r.nextDouble() * 100)) / 100.0;
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        return nf.format(number);
    }

    public static String getCurrentDate(){
        Date d = new Date();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(d);
    }

    public static int getWareHouse(){
        Random r = new Random();
        return 1 + r.nextInt(10);
    }
}
