module ecommerce.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
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