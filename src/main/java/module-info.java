module org.example.quanlyxemay {
    requires javafx.controls;
    requires javafx.fxml;
    requires  java.sql;


    opens org.example.quanlyxemay to javafx.fxml;
    exports org.example.quanlyxemay;
}