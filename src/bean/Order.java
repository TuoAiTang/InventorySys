package bean;

public class Order {
    private int id;
    private String goods_name;
    private int amount;
    private String date;
    private double money;
    private String status;
    public Order() {}

    public Order(int id, String goods_name, int amount, String date, double money, String status) {
        this.id = id;
        this.goods_name = goods_name;
        this.amount = amount;
        this.date = date;
        this.money = money;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
