package ru.mai.javafx.javafxcalendarapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.mai.javafx.javafxcalendarapplication.modules.DatabaseHandler;

public class UpController {

    @FXML
    private Button btnContinue;

    @FXML
    private PasswordField checkPassword;

    @FXML
    private TextField enterName;


    @FXML
    private Label justLabel;

    @FXML
    private Label justLabel1;

    @FXML
    private TextField newLogin;

    @FXML
    private PasswordField newPassword;

    @FXML
    void clickContinue () {
        signUp();
    }
    @FXML
    void signUp() {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String enteredName = enterName.getText();
        String logIn = newLogin.getText();
        String pass = newPassword.getText();
        btnContinue.setOnAction(event -> {
            dbHandler.sighUpUser(enteredName, logIn, pass);
        });
    }
}
