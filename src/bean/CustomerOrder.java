package bean;

public class CustomerOrder extends Order{
    private String user_name;
    public CustomerOrder() { super(); }

    public CustomerOrder(int id, String goods_name, int amount, String date, double money, String status, String user_name) {
        super(id, goods_name, amount, date, money, status);
        this.user_name = user_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
