package utils;

import models.Client;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ClientDAO {
    private static final DateTimeFormatter DMY = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public static void insert(Client client) throws SQLException {
        String sql = "INSERT INTO clients (name, birth_date, email, password, telefone) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, client.getName());
            stmt.setDate(2, Date.valueOf(client.getBirthDate()));
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getPassword());
            stmt.setString(5, client.getTelefone());
            
            stmt.executeUpdate();
        }
    }
    
    public static Client findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM clients WHERE email = ?";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToClient(rs);
                }
            }
        }
        return null;
    }
    
    public static Client authenticate(String email, String password) throws SQLException {
        // Primeiro, buscar o cliente pelo email
        String sql = "SELECT * FROM clients WHERE email = ?";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String dbPassword = rs.getString("password");
                    // Comparar senhas (trim para remover espaços)
                    if (dbPassword != null && dbPassword.trim().equals(password.trim())) {
                        System.out.println("Senha correta! Retornando cliente do banco.");
                        return mapResultSetToClient(rs);
                    } else {
                        System.out.println("Senha incorreta! Senha do banco: '" + dbPassword + "', Senha fornecida: '" + password + "'");
                        return null;
                    }
                } else {
                    System.out.println("Email não encontrado no banco: " + email);
                    return null;
                }
            }
        }
    }
    
    public static boolean emailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM clients WHERE email = ?";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    private static Client mapResultSetToClient(ResultSet rs) throws SQLException {
        Client client = new Client(

            rs.getString("name"),
            rs.getDate("birth_date").toLocalDate().format(DMY),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("telefone")
        );

        client.setId(rs.getInt("id"));
        
        if (rs.getString("cep") != null) {
            client.setCep(rs.getString("cep"));
        }
        if (rs.getString("cidade") != null) {
            client.setCidade(rs.getString("cidade"));
        }
        if (rs.getString("estado") != null) {
            client.setEstado(rs.getString("estado"));
        }
        if (rs.getString("pais") != null) {
            client.setPais(rs.getString("pais"));
        }
        
        return client;
    }
}

