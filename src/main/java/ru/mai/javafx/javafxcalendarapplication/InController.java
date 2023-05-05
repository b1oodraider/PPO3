package ru.mai.javafx.javafxcalendarapplication;

import animations.Shake;
import javafx.event.ActionEvent;
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

public class InController {

    @FXML
    private Button btnLetSGo;

    @FXML
    private TextField enterLogin;

    @FXML
    private PasswordField enterPassword;

    @FXML
    private Label justLabel;

    @FXML
    private Button signUp;

    private void showAlertWithHeaderText() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign in");
        alert.setHeaderText("Sign in is successfully!");
        //alert.setContentText("Sign in is successfully!");

        alert.showAndWait();
    }

    private void showAlertWithHeaderTextAlone() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign up");
        alert.setHeaderText("Sign up is successfully! You sign in already!");
        //alert.setContentText("Sign in is successfully!");
        alert.showAndWait();
    }

    private void showAlertWithHeaderTextError() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Problems with your login or password");
        alert.setHeaderText("YTy v chem-to oshibsya");
        //alert.setContentText("Sign in is successfully!");
        alert.showAndWait();
    }

    @FXML
    void openSignup() {
        signUp.setOnMouseClicked((event) -> {
            signUp.getScene().getWindow().hide();
            FXMLLoader loaderUp = new FXMLLoader();
            loaderUp.setLocation(getClass().getResource("up-window.fxml"));
            try {
                loaderUp.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent rootUp = loaderUp.getRoot();
            Stage stageUp = new Stage();
            stageUp.setScene(new Scene(rootUp));
            stageUp.show();
        });
    }

    public void loginUser(String loginText, String passwordText, boolean isAfterSignUp) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(loginText);
        user.setPassword(passwordText);
        ResultSet result = dbHandler.getUser(user);

        try {
            if (result.next()) {
                try {
                    btnLetSGo.getScene().getWindow().hide();
                } catch (NullPointerException ignore) {

                }
                if (!isAfterSignUp) {
                    showAlertWithHeaderText();
                } else {
                    showAlertWithHeaderTextAlone();
                }
                System.out.println("Авторизация прошла успешно");
                //CalendarController calendarController = new CalendarController();
                //calendarController.changeUserName(loginText);

            } else {
                Shake loginAnimation = new Shake(enterLogin);
                Shake passwordAnimation = new Shake(enterPassword);
                loginAnimation.playAnimation();
                passwordAnimation.playAnimation();
                showAlertWithHeaderTextError();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clickBtnLetsGo(ActionEvent event) {
        String loginText = enterLogin.getText().trim();
        String passwordText = enterPassword.getText();
        boolean isAfterSignUp = false;
        loginUser(loginText, passwordText, isAfterSignUp);
    }
}
