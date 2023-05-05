package ru.mai.javafx.javafxcalendarapplication;

import animations.Shake;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ru.mai.javafx.javafxcalendarapplication.modules.DatabaseHandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpController {

    @FXML
    private Button btnContinue;

    @FXML
    private PasswordField checkPassword;

    @FXML
    public TextField enterName;

    @FXML
    private Label justLabel;

    @FXML
    private Label justLabel1;

    @FXML
    private TextField newLogin;

    @FXML
    private PasswordField newPassword;

    private void showAlertWithHeaderTextError() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Problems with your Sign up");
        alert.setHeaderText("This user already exists");
        //alert.setContentText("Sign in is successfully!");
        alert.showAndWait();
    }

    private void showAlertWithHeaderTextErrorPassword() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Password dont ravno");
        alert.setHeaderText("-");
        //alert.setContentText("Sign in is successfully!");
        alert.showAndWait();
    }

    @FXML
    void clickContinue () {
        boolean isAfterSignUp = false;
        String userName = newLogin.getText().trim();
        String password = newPassword.getText();

        InController inController = new InController();

        if (checkUserOnBD(userName, password) == true) {
            System.out.println("Пользователь с таким логином и паролем уже существует");
        } else {
            signUp();
            isAfterSignUp = true;
            inController.loginUser(userName, password, isAfterSignUp);
        }
    }
    @FXML
    void signUp() {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String firstName = enterName.getText().trim();
        String userName = newLogin.getText().trim();
        String password = newPassword.getText();
        String checkPass = checkPassword.getText();

        Shake nameAnimation = new Shake(enterName);
        Shake userNameAnimation = new Shake(newLogin);
        Shake passwordAnimation = new Shake(newPassword);
        Shake checkPasswordAnimation = new Shake(checkPassword);

        User user = new User(firstName, userName, password, checkPass);

        if (!firstName.equals("") && !userName.equals("") && !password.equals("")) {
            if (!checkPass.equals(password)) {
                passwordAnimation.playAnimation();
                checkPasswordAnimation.playAnimation();
                showAlertWithHeaderTextErrorPassword();
            } else {
                dbHandler.sighUpUser(user);
                btnContinue.getScene().getWindow().hide();
                System.out.println("Регистрация прошла успешно");
            }
        } else {
            nameAnimation.playAnimation();
            userNameAnimation.playAnimation();
            passwordAnimation.playAnimation();
            checkPasswordAnimation.playAnimation();
            showAlertWithHeaderTextError();
        }
    }
    public boolean checkUserOnBD(String userName, String password) {
        userName = newLogin.getText().trim();
        password = newPassword.getText();
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        ResultSet result = dbHandler.getUser(user);

        try {
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
