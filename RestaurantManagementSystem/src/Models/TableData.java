package Models;

public class TableData {
    private int id;
    private String tableNo;
    private double totalPrice;
    private int orderCount;

    public TableData(int id, String tableNo, double totalPrice, int orderCount) {
        this.id = id;
        this.tableNo = tableNo;
        this.totalPrice = totalPrice;
        this.orderCount = orderCount;
    }

    public int getId() {
        return id;
    }

    public String getTableNo() {
        return tableNo;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getOrderCount() {
        return orderCount;
    }
}
