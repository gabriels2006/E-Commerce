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

        // 🔒 Validação dos dados antes de salvar
        if (order == null) throw new IllegalArgumentException("Pedido está nulo.");
        if (order.getClient() == null) throw new IllegalArgumentException("Cliente do pedido está nulo.");
        if (order.getClient().getEmail() == null || order.getClient().getEmail().isBlank())
            throw new IllegalArgumentException("Email do cliente está vazio.");
        if (order.getOrderDate() == null) throw new IllegalArgumentException("Data do pedido está nula.");
        if (order.getItems() == null || order.getItems().isEmpty())
            throw new IllegalArgumentException("Pedido não tem itens.");

        String sql = "INSERT INTO orders (client_email, order_date) VALUES (?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Log para debug
            System.out.println(">>> Inserindo pedido para cliente: " + order.getClient().getEmail());
            System.out.println(">>> Data do pedido: " + order.getOrderDate());

            stmt.setString(1, order.getClient().getEmail());
            stmt.setDate(2, Date.valueOf(order.getOrderDate()));

            stmt.executeUpdate();

            // Obter o ID do pedido inserido
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);
                    System.out.println(">>> Pedido inserido com ID: " + orderId);

                    // Inserir itens do pedido
                    insertOrderItems(conn, orderId, order.getItems());
                } else {
                    throw new SQLException("Falha ao obter ID do pedido inserido.");
                }
            }

        } catch (SQLException e) {
            System.err.println("✗ Erro ao salvar pedido no banco: " + e.getMessage());
            e.printStackTrace(); // mostra a stack completa no console
            throw e; // repropaga para ver o erro real
        }
    }

    // Inserir itens do pedido
    private static void insertOrderItems(Connection conn, int orderId, List<OrderItem> items) throws SQLException {
        //campo subtotal no INSERT antes dava erro, agora, não mais hehe
        String sql = "INSERT INTO order_items (order_id, product_name, quantity, unit_price, subtotal) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        for (OrderItem item : items) {
            int qty = item.getQuantity();
            float price = item.getUnitPrice(); // já vem do OrderItem
            float subtotal = qty * price;      // calculamos aqui

            System.out.println(">>> Inserindo item: " + item.getProduct().getName() +
                    " | qtd=" + qty +
                    " | preço=" + price +
                    " | subtotal=" + subtotal);

            stmt.setInt(1, orderId);
            stmt.setString(2, item.getProduct().getName());
            stmt.setInt(3, qty);
            stmt.setFloat(4, price);
            stmt.setFloat(5, subtotal); // novo campo
            stmt.addBatch();
        }
        stmt.executeBatch();
    }
}

    // Buscar pedidos de um cliente
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
                    // Se quiser, pode setar também o cliente:
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

    // Buscar itens de um pedido
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

                    // Buscar o produto completo pelo nome
                    Product product = ProductDAO.findByName(productName);

                    OrderItem item = new OrderItem(product, quantity, unitPrice);
                    items.add(item);
                }
            }
        }
        return items;
    }
}

