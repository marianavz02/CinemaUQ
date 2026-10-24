module org.example.cinemauq {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.cinemauq to javafx.fxml;
    exports org.example.cinemauq;
    exports controller;
    opens controller to javafx.fxml;
    exports viewController;
    opens viewController to javafx.fxml;
}