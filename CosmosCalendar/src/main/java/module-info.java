module ru.mai.javafx.javafxcalendarapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens ru.mai.javafx.javafxcalendarapplication to javafx.fxml;
    exports ru.mai.javafx.javafxcalendarapplication;
}