package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private int id;
    private Client client;              // Cliente que fez o pedido
    private List<OrderItem> items;      // Lista de produtos + quantidade
    private LocalDate orderDate;        // Data do pedido

    // Construtor
    public Order(Client client) {
        this.client = client;
        this.items = new ArrayList<>();
        this.orderDate = LocalDate.now();
    }

    public Order() {

    }

    // Adicionar produto ao pedido
    public void addProduct(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade inválida.");
        }
        float unitPrice = product.getFinalPrice(); // ou product.getPrice()
        items.add(new OrderItem(product, quantity, unitPrice));
    }

    // Confirmar pedido (reduz estoque dos produtos)
    public void confirmOrder() {
        for (OrderItem item : items) {
            item.getProduct().reduceStock(item.getQuantity());
        }
        System.out.println("Pedido confirmado! Estoque atualizado.");
    }

    // Getters
    public Client getClient() {
        return client;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setId(int id) { this.id = id; }

    public void setOrderDate(LocalDate date) { this.orderDate = date; }

    public void setItems(List<OrderItem> items) { this.items = items; }

}
