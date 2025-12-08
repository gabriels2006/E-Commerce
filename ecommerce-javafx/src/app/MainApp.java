package app;

import javafx.application.Application;
import javafx.stage.Stage;
import views.LoginView;

public class MainApp extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("E-Commerce de Eletrônicos");
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.setResizable(false);

        LoginView loginView = new LoginView();
        primaryStage.setScene(loginView.getScene());
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}