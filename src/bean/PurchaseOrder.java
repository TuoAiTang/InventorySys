package bean;

public class PurchaseOrder extends Order{
    public PurchaseOrder() {super();}
    public PurchaseOrder(int id, String goods_name, int amount, String date, double money, String status) {
        super(id, goods_name, amount, date, money, status);
    }
}

