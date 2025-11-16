package utils;

import models.Product;
import models.OrderItem;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    
    private static Cart instance;
    private List<OrderItem> items;
    
    private Cart() {
        items = new ArrayList<>();
    }
    
    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }
    
    public void addItem(Product product, int quantity) {
        // Verificar se o produto já está no carrinho
        for (OrderItem item : items) {
            if (item.getProduct().equals(product)) {
                // Atualizar quantidade
                items.remove(item);
                items.add(new OrderItem(product, item.getQuantity() + quantity));
                return;
            }
        }
        // Adicionar novo item
        items.add(new OrderItem(product, quantity));
    }
    
    public void removeItem(OrderItem item) {
        items.remove(item);
    }
    
    public void updateQuantity(OrderItem item, int newQuantity) {
        if (newQuantity <= 0) {
            removeItem(item);
        } else {
            items.remove(item);
            items.add(new OrderItem(item.getProduct(), newQuantity));
        }
    }
    
    public List<OrderItem> getItems() {
        return items;
    }
    
    public float getTotal() {
        float total = 0;
        for (OrderItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }
    
    public int getItemCount() {
        return items.size();
    }
    
    public void clear() {
        items.clear();
    }
    
    public boolean isEmpty() {
        return items.isEmpty();
    }
}
