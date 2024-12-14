package Models;

public class OrderDetail {
    private int id;
    private Order order;  // Sipariş
    private Product product;  // Ürün
    private int quantity;  // Miktar

    // Constructor
    public OrderDetail(int id, Order order, Product product, int quantity) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }
    
    

  
    public int getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Ürün: " + product.getName() + ", Miktar: " + quantity;
    }
}
