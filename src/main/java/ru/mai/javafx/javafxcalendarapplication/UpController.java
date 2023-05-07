package ru.mai.javafx.javafxcalendarapplication;

import animations.Shake;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.mai.javafx.javafxcalendarapplication.modules.DatabaseHandler;
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
        alert.setTitle("Problems with your sign up");
        alert.setHeaderText("This user already exists");

        alert.showAndWait();
    }

    private void showAlertWithHeaderTextErrorPassword() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Problems with your sign up");
        alert.setHeaderText("Please make sure your passwords match");

        alert.showAndWait();
    }

    @FXML
    void clickContinue () {
        String userName = newLogin.getText().trim();
        String password = newPassword.getText();

        if (checkUserOnBD(userName, password) == true) {
            showAlertWithHeaderTextError();
        } else {
            signUp();
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

        if (firstName.equals("") | userName.equals("") | password.equals("")) {
            nameAnimation.playAnimation();
            userNameAnimation.playAnimation();
            passwordAnimation.playAnimation();
            checkPasswordAnimation.playAnimation();
        } else if (!checkPass.equals(password)) {
            passwordAnimation.playAnimation();
            checkPasswordAnimation.playAnimation();
            showAlertWithHeaderTextErrorPassword();
        } else {
            dbHandler.sighUpUser(user);
            btnContinue.getScene().getWindow().hide();

            boolean isAfterSignUp = true;
            InController inController = new InController();
            inController.loginUser(userName, password, isAfterSignUp);
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
