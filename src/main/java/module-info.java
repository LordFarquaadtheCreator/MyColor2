module com.example.mycolor2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.mycolor2 to javafx.fxml;
    exports com.example.mycolor2;
}