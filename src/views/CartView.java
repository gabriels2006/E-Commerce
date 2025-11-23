package views;

import app.MainApp;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.OrderItem;
import models.Order;
import utils.Cart;
import utils.DataStore;

public class CartView {
    
    private Scene scene;
    private Cart cart;
    private DataStore dataStore;
    private TableView<OrderItem> table;
    private Label totalLabel;

    public CartView() {
        cart = Cart.getInstance();
        dataStore = DataStore.getInstance();
        createScene();
    }
    
    private void createScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        // Header
        HBox header = createHeader();
        root.setTop(header);
        
        // Centro com tabela
        VBox centerBox = new VBox(20);
        centerBox.setPadding(new Insets(30));
        
        Label titleLabel = new Label("Meu Carrinho de Compras");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web("#2c3e50"));
        
        if (cart.isEmpty()) {
            VBox emptyBox = new VBox(20);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(100));
            
            Label emptyLabel = new Label("Seu carrinho está vazio");
            emptyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            emptyLabel.setTextFill(Color.web("#95a5a6"));
            
            Button backButton = new Button("Voltar às Compras");
            backButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 30; -fx-cursor: hand;");
            backButton.setOnAction(e -> {
                MainView mainView = new MainView();
                MainApp.getPrimaryStage().setScene(mainView.getScene());
            });
            
            emptyBox.getChildren().addAll(emptyLabel, backButton);
            centerBox.getChildren().addAll(titleLabel, emptyBox);
        } else {
            table = createTable();
            
            // Painel de resumo
            VBox summaryBox = createSummaryBox();
            
            centerBox.getChildren().addAll(titleLabel, table, summaryBox);
        }
        
        root.setCenter(centerBox);
        
        scene = new Scene(root, 1200, 800);
    }
    
    private HBox createHeader() {
        HBox header = new HBox(20);
        header.setPadding(new Insets(15, 30, 15, 30));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #2c3e50;");
        
        Button backButton = new Button("← Voltar");
        backButton.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 20; -fx-cursor: hand;");
        backButton.setOnAction(e -> {
            MainView mainView = new MainView();
            MainApp.getPrimaryStage().setScene(mainView.getScene());
        });
        
        Label titleLabel = new Label("Carrinho de Compras");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.WHITE);
        
        header.getChildren().addAll(backButton, titleLabel);
        
        return header;
    }
    
    private TableView<OrderItem> createTable() {
        table = new TableView<>();
        table.setStyle("-fx-background-color: white;");
        
        // Coluna Produto
        TableColumn<OrderItem, String> productCol = new TableColumn<>("Produto");
        productCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProduct().getName()));
        productCol.setPrefWidth(350);
        
        // Coluna Preço Unitário
        TableColumn<OrderItem, String> priceCol = new TableColumn<>("Preço Unitário");
        priceCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                String.format("R$ %.2f", cellData.getValue().getProduct().getFinalPrice())));
        priceCol.setPrefWidth(150);
        
        // Coluna Quantidade
        TableColumn<OrderItem, Integer> quantityCol = new TableColumn<>("Quantidade");
        quantityCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        quantityCol.setPrefWidth(120);
        
        // Coluna Subtotal
        TableColumn<OrderItem, String> subtotalCol = new TableColumn<>("Subtotal");
        subtotalCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                String.format("R$ %.2f", cellData.getValue().getTotalPrice())));
        subtotalCol.setPrefWidth(150);
        
        // Coluna Ações
        TableColumn<OrderItem, Void> actionCol = new TableColumn<>("Ações");
        actionCol.setPrefWidth(150);
        
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button removeButton = new Button("Remover");
            
            {
                removeButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 12px; -fx-cursor: hand;");
                removeButton.setOnAction(e -> {
                    OrderItem item = getTableView().getItems().get(getIndex());
                    cart.removeItem(item);
                    refreshTable();
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeButton);
                }
            }
        });
        
        table.getColumns().addAll(productCol, priceCol, quantityCol, subtotalCol, actionCol);
        table.getItems().addAll(cart.getItems());
        
        return table;
    }
    
    private VBox createSummaryBox() {
        VBox summaryBox = new VBox(15);
        summaryBox.setPadding(new Insets(20));
        summaryBox.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 2);");
        summaryBox.setMaxWidth(400);
        summaryBox.setAlignment(Pos.CENTER_RIGHT);
        
        HBox totalBox = new HBox(20);
        totalBox.setAlignment(Pos.CENTER_RIGHT);
        
        Label totalTextLabel = new Label("Total:");
        totalTextLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        totalLabel = new Label(String.format("R$ %.2f", cart.getTotal()));
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        totalLabel.setTextFill(Color.web("#27ae60"));
        
        totalBox.getChildren().addAll(totalTextLabel, totalLabel);
        
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        
        Button clearButton = new Button("Limpar Carrinho");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 25; -fx-cursor: hand;");
        clearButton.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmação");
            confirm.setHeaderText("Limpar carrinho?");
            confirm.setContentText("Deseja realmente remover todos os itens do carrinho?");
            
            if (confirm.showAndWait().get() == ButtonType.OK) {
                cart.clear();
                CartView newCartView = new CartView();
                MainApp.getPrimaryStage().setScene(newCartView.getScene());
            }
        });
        
        Button checkoutButton = new Button("Finalizar Pedido");
        checkoutButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 25; -fx-cursor: hand;");
        checkoutButton.setOnAction(e -> finalizePurchase());
        
        buttonBox.getChildren().addAll(clearButton, checkoutButton);
        
        summaryBox.getChildren().addAll(new Separator(), totalBox, buttonBox);
        
        return summaryBox;
    }
    
    private void refreshTable() {
        table.getItems().clear();
        table.getItems().addAll(cart.getItems());
        totalLabel.setText(String.format("R$ %.2f", cart.getTotal()));
        
        if (cart.isEmpty()) {
            CartView newCartView = new CartView();
            MainApp.getPrimaryStage().setScene(newCartView.getScene());
        }
    }
    
    private void finalizePurchase() {
        try {
            // Criar pedido
            Order order = new Order(dataStore.getCurrentClient());
            
            for (OrderItem item : cart.getItems()) {
                order.addProduct(item.getProduct(), item.getQuantity());
            }
            
            // Confirmar pedido (reduz estoque)
            order.confirmOrder();
            
            // Salvar pedido
            dataStore.addOrder(order);
            
            // Limpar carrinho
            cart.clear();
            
            // Mostrar confirmação
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pedido Confirmado");
            alert.setHeaderText("Compra realizada com sucesso!");
            alert.setContentText("Seu pedido foi confirmado e o estoque foi atualizado.\nObrigado por comprar conosco!");
            alert.showAndWait();
            
            // Voltar para tela principal
            MainView mainView = new MainView();
            MainApp.getPrimaryStage().setScene(mainView.getScene());
            
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao processar pedido");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    
    public Scene getScene() {
        return scene;
    }
}
