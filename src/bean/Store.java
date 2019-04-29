package bean;

public class Store {
    private int id;
    private String goods_name;
    private int amount;
    private String offline_name;

    public Store() {
    }

    public Store(int id, String goods_name, int amount, String offline_name) {
        this.id = id;
        this.goods_name = goods_name;
        this.amount = amount;
        this.offline_name = offline_name;
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

    public String getOffline_name() {
        return offline_name;
    }

    public void setOffline_name(String offline_name) {
        this.offline_name = offline_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
