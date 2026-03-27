module org.example.fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    opens org.example.fx to javafx.fxml;
    exports org.example.fx;
}