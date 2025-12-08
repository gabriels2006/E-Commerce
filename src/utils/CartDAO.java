package utils;

import models.OrderItem;
import models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    // Adicionar item ao carrinho
    public static void addItem(int clientId, OrderItem item) throws SQLException {
        String sql = "INSERT INTO cart_items(client_id, product_id, quantity, unit_price, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            float subtotal = item.getQuantity() * item.getUnitPrice();

            stmt.setInt(1, clientId);
            stmt.setInt(2, item.getProduct().getId()); // agora usa product_id
            stmt.setInt(3, item.getQuantity());
            stmt.setFloat(4, item.getUnitPrice());
            stmt.setFloat(5, subtotal);

            stmt.executeUpdate();
        }
    }

    // Carregar carrinho de um cliente
    public static List<OrderItem> loadCart(int clientId) throws SQLException {
        String sql = "SELECT * FROM cart_items WHERE client_id = ?";
        List<OrderItem> items = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("product_id");
                    int qty = rs.getInt("quantity");
                    float unitPrice = rs.getFloat("unit_price");

                    // Busca o produto pelo ID
                    Product product = ProductDAO.findById(productId);
                    OrderItem item = new OrderItem(product, qty, unitPrice);
                    items.add(item);
                }
            }
        }
        return items;
    }

    // Limpar carrinho de um cliente
    public static void clearCart(int clientId) throws SQLException {
        String sql = "DELETE FROM cart_items WHERE client_id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            stmt.executeUpdate();
        }
    }

    // Remover item específico do carrinho
    public static void removeItem(int clientId, int productId) throws SQLException {
        String sql = "DELETE FROM cart_items WHERE client_id = ? AND product_id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        }
    }
}




