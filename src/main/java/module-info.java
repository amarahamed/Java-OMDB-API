module com.example.omdbapi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;
    requires java.net.http;

    opens com.example.omdbapi to javafx.fxml,com.google.gson;
    exports com.example.omdbapi;
}