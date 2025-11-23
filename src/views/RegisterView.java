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
import controllers.RegisterController;

public class RegisterView {
    
    private Scene scene;
    private RegisterController controller;
    
    public RegisterView() {
        controller = new RegisterController();
        createScene();
    }
    
    private void createScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        // ScrollPane para permitir rolagem
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #f5f5f5; -fx-background-color: transparent;");
        
        VBox centerBox = new VBox(15);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(40));
        centerBox.setMaxWidth(500);
        centerBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        
        // Título
        Label titleLabel = new Label("Criar Nova Conta");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titleLabel.setTextFill(Color.web("#2c3e50"));
        
        Label subtitleLabel = new Label("Preencha os dados abaixo");
        subtitleLabel.setFont(Font.font("Arial", 13));
        subtitleLabel.setTextFill(Color.web("#7f8c8d"));
        
        // Campos
        TextField nameField = createTextField("Nome Completo", "João Silva");
        TextField birthDateField = createTextField("Data de Nascimento", "DD/MM/AAAA");
        TextField emailField = createTextField("E-mail", "seu@email.com");
        PasswordField passwordField = createPasswordField("Senha", "Mínimo 8 caracteres");
        PasswordField confirmPasswordField = createPasswordField("Confirmar Senha", "Digite a senha novamente");
        TextField phoneField = createTextField("Telefone", "(11) 98765-4321");
        
        // Label de erro
        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(Font.font("Arial", 12));
        errorLabel.setVisible(false);
        errorLabel.setWrapText(true);
        errorLabel.setMaxWidth(450);
        
        // Label de requisitos da senha
        Label passwordRequirements = new Label("A senha deve conter: mínimo 8 caracteres, letras, números e símbolos (@$!%*?&)");
        passwordRequirements.setFont(Font.font("Arial", 11));
        passwordRequirements.setTextFill(Color.web("#7f8c8d"));
        passwordRequirements.setWrapText(true);
        passwordRequirements.setMaxWidth(450);
        
        // Botões
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button registerButton = new Button("Cadastrar");
        registerButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 40; -fx-cursor: hand;");
        
        Button backButton = new Button("Voltar");
        backButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 40; -fx-cursor: hand;");
        
        buttonBox.getChildren().addAll(backButton, registerButton);
        
        // Eventos
        registerButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String birthDate = birthDateField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String phone = phoneField.getText().trim();
            
            if (name.isEmpty() || birthDate.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                errorLabel.setText("Preencha todos os campos!");
                errorLabel.setVisible(true);
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                errorLabel.setText("As senhas não coincidem!");
                errorLabel.setVisible(true);
                return;
            }
            
            String result = controller.register(name, birthDate, email, password, phone);
            
            if (result.equals("SUCCESS")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText("Cadastro realizado!");
                alert.setContentText("Sua conta foi criada com sucesso. Faça login para continuar.");
                alert.showAndWait();
                
                LoginView loginView = new LoginView();
                MainApp.getPrimaryStage().setScene(loginView.getScene());
            } else {
                errorLabel.setText(result);
                errorLabel.setVisible(true);
            }
        });
        
        backButton.setOnAction(e -> {
            LoginView loginView = new LoginView();
            MainApp.getPrimaryStage().setScene(loginView.getScene());
        });
        
        // Adicionar elementos
        centerBox.getChildren().addAll(
            titleLabel,
            subtitleLabel,
            new Separator(),
            createLabel("Nome:"),
            nameField,
            createLabel("Data de Nascimento:"),
            birthDateField,
            createLabel("E-mail:"),
            emailField,
            createLabel("Senha:"),
            passwordField,
            passwordRequirements,
            createLabel("Confirmar Senha:"),
            confirmPasswordField,
            createLabel("Telefone:"),
            phoneField,
            errorLabel,
            buttonBox
        );
        
        StackPane container = new StackPane(centerBox);
        container.setPadding(new Insets(30));
        
        scrollPane.setContent(container);
        root.setCenter(scrollPane);
        
        scene = new Scene(root, 1200, 800);
    }
    
    private TextField createTextField(String label, String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setStyle("-fx-font-size: 13px; -fx-padding: 8;");
        return field;
    }
    
    private PasswordField createPasswordField(String label, String prompt) {
        PasswordField field = new PasswordField();
        field.setPromptText(prompt);
        field.setStyle("-fx-font-size: 13px; -fx-padding: 8;");
        return field;
    }
    
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 13));
        return label;
    }
    
    public Scene getScene() {
        return scene;
    }
}

