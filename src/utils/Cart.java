package utils;

import models.Client;
import models.Product;
import models.OrderItem;
import java.sql.SQLException;
import java.util.List;

public class Cart {

    private static Cart instance;

    private Cart() {}

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    // Adicionar item direto no banco
    public void addItem(Product product, int quantity) {
        float unitPrice = product.getFinalPrice();
        OrderItem newItem = new OrderItem(product, quantity, unitPrice);

        try {
            int clientId = DataStore.getInstance().getCurrentClient().getId();
            CartDAO.addItem(clientId, newItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Atualizar quantidade (reinsere no banco)
    public void updateQuantity(OrderItem item, int newQuantity) {
        try {
            int clientId = DataStore.getInstance().getCurrentClient().getId();
            CartDAO.clearCart(clientId); // limpa tudo
            if (newQuantity > 0) {
                OrderItem newItem = new OrderItem(item.getProduct(), newQuantity, item.getProduct().getFinalPrice());
                CartDAO.addItem(clientId, newItem); // reinsere
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Carregar carrinho do banco
    public List<OrderItem> getItems() {
        Client current = DataStore.getInstance().getCurrentClient();
        if (current == null) {
            // Nenhum cliente logado → retorna lista vazia
            return List.of();
        }
        try {
            return CartDAO.loadCart(current.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }


    // Remover item específico
    public void removeItem(OrderItem item) {
        try {
            int clientId = DataStore.getInstance().getCurrentClient().getId();
            int productId = item.getProduct().getId();
            CartDAO.removeItem(clientId, productId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Limpar carrinho no banco
    public void clear() {
        try {
            int clientId = DataStore.getInstance().getCurrentClient().getId();
            CartDAO.clearCart(clientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isEmpty() {
        return getItems().isEmpty();
    }
}

