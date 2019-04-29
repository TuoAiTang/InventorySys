package bean;

public class Supp {
    private String goods_name;
    private int amount;
    private double price;
    public Supp(){}
    public Supp(String goods_name, int amount, double price) {
        this.goods_name = goods_name;
        this.amount = amount;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
