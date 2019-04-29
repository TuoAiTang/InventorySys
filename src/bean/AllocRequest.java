package bean;

public class AllocRequest {
    private int id;
    private String offline_name;
    private String goods_name;
    private int amount;
    private String date;
    private String status;

    public AllocRequest(){}
    public AllocRequest(int id, String offline_name, String goods_name, int amount, String date, String status) {
        this.id = id;
        this.offline_name = offline_name;
        this.goods_name = goods_name;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOffline_name() {
        return offline_name;
    }

    public void setOffline_name(String offline_name) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
