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
    
    private List<Client> clients;
    private List<Product> products;
    private List<Order> orders;
    private Client currentClient;
    
    private DataStore() {
        clients = new ArrayList<>(); // Apenas para cache local, não como fonte de dados
        products = new ArrayList<>(); // Apenas para popular o banco na primeira vez
        orders = new ArrayList<>(); // Apenas para cache local

        // Verificar conexão com banco de dados
        try {
            if (!DataBaseConnection.testConnection()) {
                System.err.println("⚠ AVISO: Não foi possível conectar ao banco de dados MySQL.");
                System.err.println("Certifique-se de que o MySQL está rodando e que o banco 'ecommerce' existe.");
                return; // Não popula produtos se não conectar
            }

            // Popular produtos iniciais no banco (se necessário)
            initializeProducts();
            populateDatabaseWithProducts();
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
    
    private void populateDatabaseWithProducts() {
        try {
            // Verificar se já existem produtos no banco
            List<Product> dbProducts = ProductDAO.findAll();
            if (!dbProducts.isEmpty()) {
                System.out.println("✓ Banco de dados já contém " + dbProducts.size() + " produtos.");
                products.clear(); // Limpar lista mesmo se já tiver produtos
                return; // Já tem produtos, não precisa popular
            }
            
            // Popular o banco com os produtos iniciais
            System.out.println("Populando banco de dados com produtos iniciais...");
            int inserted = 0;
            int skipped = 0;
            
            for (Product product : products) {
                try {
                    // Verificar se o produto já existe
                    if (!ProductDAO.productExists(product.getName())) {
                        ProductDAO.insert(product);
                        inserted++;
                        System.out.println("  ✓ Inserido: " + product.getName());
                    } else {
                        skipped++;
                    }
                } catch (SQLException e) {
                    System.err.println("  ✗ Erro ao inserir produto " + product.getName() + ": " + e.getMessage());
                    // Continua tentando inserir os outros produtos
                }
            }
            
            System.out.println("✓ População concluída: " + inserted + " produtos inseridos, " + skipped + " já existiam.");
            
            // Limpar lista de produtos da memória após popular o banco
            products.clear();
            
        } catch (SQLException e) {
            System.err.println("✗ Erro ao popular banco de dados: " + e.getMessage());
            // Não lança exceção, apenas loga o erro
        }
    }

    private void initializeProducts() {
        // Produtos padrão em memória (usado como fallback)
        // Notebooks
        products.add(new Product("Notebook Dell XPS 13", "Ultrabook leve e potente, ideal para trabalho e estudos",
            4500.0f, 15, "Notebooks", "Dell", "XPS 13", "Dell Inc.", LocalDate.of(2023, 5, 10)));

        products.add(new Product("Notebook Lenovo ThinkPad", "Notebook corporativo com alta durabilidade",
            5200.0f, 10, "Notebooks", "Lenovo", "ThinkPad X1", "Lenovo", LocalDate.of(2023, 6, 15)));

        products.add(new Product("Notebook Asus ROG", "Notebook gamer de alta performance",
            8900.0f, 8, "Notebooks", "Asus", "ROG Strix", "Asus", LocalDate.of(2023, 7, 20)));

        // Smartphones
        products.add(new Product("Samsung Galaxy S23", "Smartphone premium com câmera de 200MP",
            3800.0f, 25, "Smartphones", "Samsung", "Galaxy S23", "Samsung Electronics", LocalDate.of(2023, 3, 1)));

        products.add(new Product("iPhone 15 Pro", "iPhone com chip A17 Pro e titânio",
            7500.0f, 12, "Smartphones", "Apple", "iPhone 15 Pro", "Apple Inc.", LocalDate.of(2023, 9, 15)));

        products.add(new Product("Xiaomi Redmi Note 13", "Smartphone com excelente custo-benefício",
            1500.0f, 30, "Smartphones", "Xiaomi", "Redmi Note 13", "Xiaomi", LocalDate.of(2023, 8, 10)));

        // Periféricos
        products.add(new Product("Mouse Logitech MX Master 3", "Mouse ergonômico sem fio",
            450.0f, 40, "Periféricos", "Logitech", "MX Master 3", "Logitech", LocalDate.of(2023, 1, 15)));

        products.add(new Product("Teclado Mecânico Keychron K2", "Teclado mecânico wireless",
            650.0f, 20, "Periféricos", "Keychron", "K2", "Keychron", LocalDate.of(2023, 2, 20)));

        products.add(new Product("Webcam Logitech C920", "Webcam Full HD 1080p",
            380.0f, 35, "Periféricos", "Logitech", "C920", "Logitech", LocalDate.of(2023, 4, 5)));

        // Monitores
        products.add(new Product("Monitor LG UltraWide 29''", "Monitor ultrawide 29 polegadas",
            1800.0f, 18, "Monitores", "LG", "29WP500", "LG Electronics", LocalDate.of(2023, 5, 25)));

        products.add(new Product("Monitor Samsung Odyssey G5", "Monitor gamer curvo 27'' 144Hz",
            2200.0f, 12, "Monitores", "Samsung", "Odyssey G5", "Samsung Electronics", LocalDate.of(2023, 6, 30)));

        // Fones de Ouvido
        products.add(new Product("Fone Sony WH-1000XM5", "Fone com cancelamento de ruído premium",
            1900.0f, 22, "Áudio", "Sony", "WH-1000XM5", "Sony Corporation", LocalDate.of(2023, 3, 12)));

        products.add(new Product("AirPods Pro 2", "Fones true wireless da Apple",
            2100.0f, 28, "Áudio", "Apple", "AirPods Pro 2", "Apple Inc.", LocalDate.of(2023, 9, 20)));

        // Tablets
        products.add(new Product("iPad Air M2", "Tablet Apple com chip M2",
            5500.0f, 10, "Tablets", "Apple", "iPad Air", "Apple Inc.", LocalDate.of(2023, 10, 5)));

        products.add(new Product("Samsung Galaxy Tab S9", "Tablet Android premium",
            3200.0f, 15, "Tablets", "Samsung", "Galaxy Tab S9", "Samsung Electronics", LocalDate.of(2023, 8, 15)));
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
