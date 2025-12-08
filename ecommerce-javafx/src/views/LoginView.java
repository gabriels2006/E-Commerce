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
import controllers.LoginController;

public class LoginView {
    
    private Scene scene;
    private LoginController controller;
    
    public LoginView() {
        controller = new LoginController();
        createScene();
    }
    
    private void createScene() {
        // Layout principal
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        // Painel central com formulário
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(40));
        centerBox.setMaxWidth(450);
        centerBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        
        // Título
        Label titleLabel = new Label("E-Commerce de Eletrônicos");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setTextFill(Color.web("#2c3e50"));
        
        Label subtitleLabel = new Label("Faça login para continuar");
        subtitleLabel.setFont(Font.font("Arial", 14));
        subtitleLabel.setTextFill(Color.web("#7f8c8d"));
        
        // Campos de entrada
        Label emailLabel = new Label("E-mail:");
        emailLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 13));
        TextField emailField = new TextField();
        emailField.setPromptText("seu@email.com");
        emailField.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
        
        Label passwordLabel = new Label("Senha:");
        passwordLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 13));
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Digite sua senha");
        passwordField.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
        
        // Label para mensagens de erro
        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(Font.font("Arial", 12));
        errorLabel.setVisible(false);
        
        // Botões
        Button loginButton = new Button("Entrar");
        loginButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 40; -fx-cursor: hand;");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        
        Button registerButton = new Button("Criar Conta");
        registerButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 40; -fx-cursor: hand;");
        registerButton.setMaxWidth(Double.MAX_VALUE);
        
        // Eventos
        loginButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText();
            
            if (email.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Preencha todos os campos!");
                errorLabel.setVisible(true);
                return;
            }
            
            if (controller.login(email, password)) {
                MainView mainView = new MainView();
                MainApp.getPrimaryStage().setScene(mainView.getScene());
            } else {
                errorLabel.setText("E-mail ou senha incorretos!");
                errorLabel.setVisible(true);
            }
        });
        
        registerButton.setOnAction(e -> {
            RegisterView registerView = new RegisterView();
            MainApp.getPrimaryStage().setScene(registerView.getScene());
        });
        
        // Permitir login com Enter
        passwordField.setOnAction(e -> loginButton.fire());
        
        // Adicionar elementos ao layout
        centerBox.getChildren().addAll(
            titleLabel,
            subtitleLabel,
            new Separator(),
            emailLabel,
            emailField,
            passwordLabel,
            passwordField,
            errorLabel,
            loginButton,
            registerButton
        );
        
        // Container para centralizar
        StackPane container = new StackPane(centerBox);
        container.setPadding(new Insets(50));
        
        root.setCenter(container);
        
        scene = new Scene(root, 1200, 800);
    }
    
    public Scene getScene() {
        return scene;
    }
}
