package utils;

import models.Product;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    
    public static List<Product> findAll() throws SQLException {
        String sql = "SELECT * FROM products WHERE active = 1";
        List<Product> products = new ArrayList<>();
        
        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        }
        return products;
    }
    
    public static List<Product> findByCategory(String category) throws SQLException {
        String sql = "SELECT * FROM products WHERE category = ? AND active = 1";
        List<Product> products = new ArrayList<>();
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, category);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        }
        return products;
    }
    
    public static Product findById(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduct(rs);
                }
            }
        }
        return null;
    }
    
    public static void updateStock(int productId, int quantity) throws SQLException {
        String sql = "UPDATE products SET quantity_in_stock = quantity_in_stock - ? WHERE id = ?";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, quantity);
            stmt.setInt(2, productId);
            
            stmt.executeUpdate();
        }
    }
    
    public static Product findByName(String name) throws SQLException {
        String sql = "SELECT * FROM products WHERE name = ? AND active = 1";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, name);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduct(rs);
                }
            }
        }
        return null;
    }
    
    public static void insert(Product product) throws SQLException {
        String sql = "INSERT INTO products (name, description, price, quantity_in_stock, category, brand, model, manufacturer, manufacture_date, active, discount) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setFloat(3, product.getPrice());
            stmt.setInt(4, product.getQuantityInStock());
            stmt.setString(5, product.getCategory());
            stmt.setString(6, product.getBrand());
            stmt.setString(7, product.getModel());
            stmt.setString(8, product.getManufacturer());
            stmt.setDate(9, Date.valueOf(product.getManufactureDate()));
            stmt.setBoolean(10, product.isAvailable());
            stmt.setFloat(11, 0.0f); // discount padrão
            
            stmt.executeUpdate();
        }
    }
    
    public static boolean productExists(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM products WHERE name = ?";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, name);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    public static List<String> getCategories() throws SQLException {
        String sql = "SELECT DISTINCT category FROM products WHERE active = 1";
        List<String> categories = new ArrayList<>();
        
        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        }
        return categories;
    }

    private static Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product(
                rs.getString("name"),
                rs.getString("description"),
                rs.getFloat("price"),
                rs.getInt("quantity_in_stock"),
                rs.getString("category"),
                rs.getString("brand"),
                rs.getString("model"),
                rs.getString("manufacturer"),
                rs.getDate("manufacture_date").toLocalDate()
        );

        // ESSENCIAL: setar o ID do banco
        product.setId(rs.getInt("id"));

        // Se tiver campos extras, também pode setar:
        product.setDiscount(rs.getFloat("discount"));
        product.setActive(rs.getBoolean("active"));

        return product;
    }
}

