package bean;

public class WareHouse {
    private String goods_name;
    private int amount;
    private int warehouse_id;
    public WareHouse() {}
    public WareHouse(String goods_name, int amount, int warehouse_id) {
        this.goods_name = goods_name;
        this.amount = amount;
        this.warehouse_id = warehouse_id;
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

    public int getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(int warehouse_id) {
        this.warehouse_id = warehouse_id;
    }
}
