package views;

import app.MainApp;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.Product;
import utils.DataStore;
import utils.Cart;
import java.util.List;

public class MainView {
    
    private Scene scene;
    private DataStore dataStore;
    private Cart cart;
    private VBox productContainer;
    private Label cartCountLabel;
    private ComboBox<String> categoryFilter;
    
    public MainView() {
        dataStore = DataStore.getInstance();
        cart = Cart.getInstance();
        createScene();
    }
    
    private void createScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        // Header
        HBox header = createHeader();
        root.setTop(header);
        
        // Sidebar com filtros
        VBox sidebar = createSidebar();
        root.setLeft(sidebar);
        
        // Área principal com produtos
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #f5f5f5; -fx-background-color: transparent;");
        
        productContainer = new VBox(20);
        productContainer.setPadding(new Insets(20));
        
        loadProducts(dataStore.getProducts());
        
        scrollPane.setContent(productContainer);
        root.setCenter(scrollPane);
        
        scene = new Scene(root, 1200, 800);
    }
    
    private HBox createHeader() {
        HBox header = new HBox(20);
        header.setPadding(new Insets(15, 30, 15, 30));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #2c3e50;");
        
        Label titleLabel = new Label("🛒 E-Commerce de Eletrônicos");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.WHITE);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        // Informações do usuário
        Label userLabel = new Label("👤 " + dataStore.getCurrentClient().getName());
        userLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        userLabel.setTextFill(Color.WHITE);
        
        // Botão do carrinho
        Button cartButton = new Button("🛒 Carrinho");
        cartCountLabel = new Label("(" + cart.getItems().size()+ ")");
        cartCountLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cartCountLabel.setTextFill(Color.web("#f39c12"));
        
        HBox cartBox = new HBox(5, cartButton, cartCountLabel);
        cartBox.setAlignment(Pos.CENTER);
        
        cartButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 8 20; -fx-cursor: hand;");
        cartButton.setOnAction(e -> {
            CartView cartView = new CartView();
            MainApp.getPrimaryStage().setScene(cartView.getScene());
        });
        
        // Botão de logout
        Button logoutButton = new Button("Sair");
        logoutButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 8 20; -fx-cursor: hand;");
        logoutButton.setOnAction(e -> {
            System.out.println("Logout clicado!");
            dataStore.logout();
            LoginView loginView = new LoginView();
            MainApp.getPrimaryStage().setScene(loginView.getScene());
        });

        
        header.getChildren().addAll(titleLabel, spacer, userLabel, cartBox, logoutButton);
        
        return header;
    }
    
    private VBox createSidebar() {
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-width: 0 1 0 0;");
        sidebar.setPrefWidth(250);
        
        Label filterLabel = new Label("Filtrar por Categoria");
        filterLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        categoryFilter = new ComboBox<>();
        categoryFilter.getItems().add("Todas as Categorias");
        categoryFilter.getItems().addAll(dataStore.getCategories());
        categoryFilter.setValue("Todas as Categorias");
        categoryFilter.setMaxWidth(Double.MAX_VALUE);
        categoryFilter.setStyle("-fx-font-size: 13px;");
        
        categoryFilter.setOnAction(e -> {
            String selected = categoryFilter.getValue();
            if (selected.equals("Todas as Categorias")) {
                loadProducts(dataStore.getProducts());
            } else {
                loadProducts(dataStore.getProductsByCategory(selected));
            }
        });
        
        sidebar.getChildren().addAll(filterLabel, categoryFilter, new Separator());
        
        return sidebar;
    }
    
    private void loadProducts(List<Product> products) {
        productContainer.getChildren().clear();
        
        Label titleLabel = new Label("Produtos Disponíveis");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.web("#2c3e50"));
        
        productContainer.getChildren().add(titleLabel);
        
        // Grid de produtos
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        
        int col = 0;
        int row = 0;
        
        for (Product product : products) {
            VBox productCard = createProductCard(product);
            grid.add(productCard, col, row);
            
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }
        
        productContainer.getChildren().add(grid);
    }
    
    private VBox createProductCard(Product product) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 2);");
        card.setPrefWidth(280);
        card.setMinHeight(280);
        
        // Nome do produto
        Label nameLabel = new Label(product.getName());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(250);
        
        // Categoria e marca
        Label categoryLabel = new Label(product.getCategory() + " • " + product.getBrand());
        categoryLabel.setFont(Font.font("Arial", 12));
        categoryLabel.setTextFill(Color.web("#7f8c8d"));
        
        // Descrição
        Label descLabel = new Label(product.getDescription());
        descLabel.setFont(Font.font("Arial", 12));
        descLabel.setWrapText(true);
        descLabel.setMaxWidth(250);
        descLabel.setMaxHeight(60);
        
        // Preço
        Label priceLabel = new Label(String.format("R$ %.2f", product.getFinalPrice()));
        priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        priceLabel.setTextFill(Color.web("#27ae60"));
        
        // Estoque
        Label stockLabel = new Label("Estoque: " + product.getQuantityInStock() + " unidades");
        stockLabel.setFont(Font.font("Arial", 11));
        stockLabel.setTextFill(product.getQuantityInStock() > 10 ? Color.web("#27ae60") : Color.web("#e74c3c"));
        
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        
        // Quantidade
        HBox quantityBox = new HBox(10);
        quantityBox.setAlignment(Pos.CENTER);
        
        Label qtyLabel = new Label("Qtd:");
        qtyLabel.setFont(Font.font("Arial", 12));
        
        Spinner<Integer> quantitySpinner = new Spinner<>(1, product.getQuantityInStock(), 1);
        quantitySpinner.setPrefWidth(80);
        quantitySpinner.setEditable(true);
        
        quantityBox.getChildren().addAll(qtyLabel, quantitySpinner);
        
        // Botão adicionar ao carrinho
        Button addButton = new Button("Adicionar ao Carrinho");
        addButton.setMaxWidth(Double.MAX_VALUE);
        addButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10; -fx-cursor: hand;");
        
        addButton.setOnAction(e -> {
            int quantity = quantitySpinner.getValue();
            cart.addItem(product, quantity);
            cartCountLabel.setText("(" + cart.getItems().size() + ")");
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Produto adicionado!");
            alert.setContentText(product.getName() + " foi adicionado ao carrinho.");
            alert.showAndWait();
        });
        
        card.getChildren().addAll(nameLabel, categoryLabel, descLabel, priceLabel, stockLabel, spacer, quantityBox, addButton);
        
        return card;
    }
    
    public Scene getScene() {
        return scene;
    }
}
