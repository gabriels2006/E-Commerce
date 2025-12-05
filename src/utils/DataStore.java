package utils;

import models.Client;
import models.Product;
import models.Order;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    
    private static DataStore instance;

    private Client currentClient;
    
    private DataStore() {
        // Verificar conexão com banco de dados
        try {
            if (!DataBaseConnection.testConnection()) {
                System.err.println("⚠ AVISO: Não foi possível conectar ao banco de dados MySQL.");
                System.err.println("Certifique-se de que o MySQL está rodando e que o banco 'ecommerce' existe.");
                return; // Não popula produtos se não conectar
            }

        } catch (Exception e) {
            System.err.println("⚠ Erro ao inicializar banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    // Métodos para Clientes
    public void addClient(Client client) {
        try {
            ClientDAO.insert(client);
            System.out.println("✓ Cliente cadastrado no banco de dados: " + client.getEmail());
            // Não adiciona na lista de memória - tudo vem do banco
        } catch (SQLException e) {
            System.err.println("✗ Erro ao inserir cliente no banco: " + e.getMessage());
            throw new RuntimeException("Erro ao cadastrar cliente no banco de dados.", e);
        }
    }
    
    public Client authenticateClient(String email, String password) {
        try {
            System.out.println("Tentando autenticar no banco de dados: " + email);
            Client client = ClientDAO.authenticate(email, password);
            if (client != null) {
                System.out.println("✓ Autenticação bem-sucedida no banco de dados!");
                return client;
            } else {
                System.out.println("✗ Usuário não encontrado ou senha incorreta.");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("✗ Erro ao autenticar no banco de dados: " + e.getMessage());
            e.printStackTrace();
            return null; // Retorna null em vez de lançar exceção
        }
    }
    
    public boolean emailExists(String email) {
        try {
            return ClientDAO.emailExists(email);
        } catch (SQLException e) {
            System.err.println("✗ Erro ao verificar email no banco: " + e.getMessage());
            e.printStackTrace();
            return false; // Retorna false em caso de erro
        }
    }
    
    // Métodos para Produtos
    public List<Product> getProducts() {
        try {
            List<Product> dbProducts = ProductDAO.findAll();
            return dbProducts;
        } catch (SQLException e) {
            System.err.println("✗ Erro ao buscar produtos no banco: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>(); // Retorna lista vazia em caso de erro
        }
    }
    
    public List<Product> getProductsByCategory(String category) {
        try {
            return ProductDAO.findByCategory(category);
        } catch (SQLException e) {
            System.err.println("✗ Erro ao buscar produtos por categoria no banco: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>(); // Retorna lista vazia em caso de erro
        }
    }
    
    public List<String> getCategories() {
        try {
            return ProductDAO.getCategories();
        } catch (SQLException e) {
            System.err.println("✗ Erro ao buscar categorias no banco: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>(); // Retorna lista vazia em caso de erro
        }
    }
    
    public void addProduct(Product product) {
        try {
            ProductDAO.insert(product);
            System.out.println("✓ Produto adicionado no banco de dados: " + product.getName());
        } catch (SQLException e) {
            System.err.println("✗ Erro ao adicionar produto no banco: " + e.getMessage());
            throw new RuntimeException("Erro ao adicionar produto no banco de dados.", e);
        }
    }
    
    // Métodos para Pedidos
    public void addOrder(Order order) {
        try {
            OrderDAO.insert(order);
            System.out.println("✓ Pedido salvo no banco de dados.");
        } catch (SQLException e) {
            System.err.println("✗ Erro ao inserir pedido no banco: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar pedido no banco de dados.", e);
        }
    }
    
    public List<Order> getOrdersByClient(Client client) {
        try {
            return OrderDAO.findByClientEmail(client.getEmail());
        } catch (SQLException e) {
            System.err.println("✗ Erro ao buscar pedidos no banco: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar pedidos no banco de dados.", e);
        }
    }
    
    // Cliente atual (sessão)
    public Client getCurrentClient() {
        return currentClient;
    }
    
    public void setCurrentClient(Client client) {
        this.currentClient = client;
    }
    
    public void logout() {
        this.currentClient = null;
    }
}
