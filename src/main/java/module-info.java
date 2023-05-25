module ru.mai.javafx.javafxcalendarapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ru.mai.javafx.javafxcalendarapplication to javafx.fxml;
    exports ru.mai.javafx.javafxcalendarapplication;
    exports ru.mai.javafx.javafxcalendarapplication.modules;
    opens ru.mai.javafx.javafxcalendarapplication.modules to javafx.fxml;
}