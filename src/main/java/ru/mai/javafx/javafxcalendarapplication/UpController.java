package ru.mai.javafx.javafxcalendarapplication;

import animations.Shake;
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
    public TextField enterName;


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
        }
    }
}
