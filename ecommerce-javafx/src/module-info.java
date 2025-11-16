module ecommerce.javafx {
    requires javafx.controls;
    requires javafx.fxml;

    exports app;
    exports views;
    exports controllers;
    exports models;
    exports utils;

    opens app to javafx.fxml;
    opens views to javafx.fxml;
    opens controllers to javafx.fxml;
    opens models to javafx.base;
}