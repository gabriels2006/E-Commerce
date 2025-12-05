package utils;

import models.Order;
import models.OrderItem;
import models.Product;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public static void insert(Order order) throws SQLException {
        String sql = "INSERT INTO orders (client_email, order_date) VALUES (?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, order.getClient().getEmail());
            stmt.setDate(2, Date.valueOf(order.getOrderDate()));

            stmt.executeUpdate();

            // Obter o ID do pedido inserido
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);
                    // Inserir itens do pedido
                    insertOrderItems(conn, orderId, order.getItems());
                }
            }
        }
    }

    private static void insertOrderItems(Connection conn, int orderId, List<OrderItem> items) throws SQLException {
        String sql = "INSERT INTO order_items (order_id, product_name, quantity, unit_price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (OrderItem item : items) {
                stmt.setInt(1, orderId);
                stmt.setString(2, item.getProduct().getName());
                stmt.setInt(3, item.getQuantity());
                stmt.setFloat(4, item.getProduct().getFinalPrice());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public static List<Order> findByClientEmail(String email) throws SQLException {
        String sql = "SELECT * FROM orders WHERE client_email = ? ORDER BY order_date DESC";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("id");
                    LocalDate orderDate = rs.getDate("order_date").toLocalDate();

                    // Criar objeto Order
                    Order order = new Order();
                    order.setId(orderId);
                    order.setOrderDate(orderDate);
                    // Aqui você pode setar também o cliente se tiver o objeto ClientDAO
                    // order.setClient(ClientDAO.findByEmail(email));

                    // Buscar itens do pedido
                    List<OrderItem> items = findOrderItems(conn, orderId);
                    order.setItems(items);

                    orders.add(order);
                }
            }
        }
        return orders;
    }

    private static List<OrderItem> findOrderItems(Connection conn, int orderId) throws SQLException {
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        List<OrderItem> items = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String productName = rs.getString("product_name");
                    int quantity = rs.getInt("quantity");
                    float unitPrice = rs.getFloat("unit_price");

                    // Aqui você pode buscar o Product completo se quiser
                    Product product = ProductDAO.findByName(productName);

                    OrderItem item = new OrderItem(product, quantity, unitPrice);
                    items.add(item);
                }
            }
        }
        return items;
    }


}

