package bean;

public class RunningTally {
    private int id;
    private int type;
    private double amount;
    private int order_id;
    private String date;

    public RunningTally(int id, int type, double amount, int order_id, String date) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.order_id = order_id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
