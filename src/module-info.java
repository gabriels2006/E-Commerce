module ecommerce.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    // adiciona suporte a JDBC
    requires java.sql;

    // se usar coleções utilitárias
    requires java.base;


    opens app to javafx.fxml;
    opens views to javafx.fxml;
    opens controllers to javafx.fxml;
    opens models to javafx.base;

    exports app;
    exports views;
    exports controllers;
    exports models;
    exports utils;
}