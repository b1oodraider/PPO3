package ru.mai.javafx.javafxcalendarapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class InController {

    @FXML
    private Button btnLetSGo;

    @FXML
    void clickBtnLetsGo(ActionEvent event) {

    }

    @FXML
    private TextField enterLogin;

    @FXML
    private PasswordField enterPassword;

    @FXML
    private Label justLabel;

    @FXML
    private Button signUp;

    @FXML
    void openSignup() {
        signUp.setOnMouseClicked((event) -> {
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

}
