package models;

public class OrderItem {
    private Product product;
    private int quantity;
    private float unitPrice;


    public OrderItem(Product product, int quantity, float unitPrice) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantidade inválida.");
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public float getUnitPrice() { return unitPrice; }
    public float getTotalPrice() {
        return product.getFinalPrice() * quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "product=" + product.getName() +
                ", quantity=" + quantity +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
}