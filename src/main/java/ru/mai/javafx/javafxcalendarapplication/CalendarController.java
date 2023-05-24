package ru.mai.javafx.javafxcalendarapplication;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import ru.mai.javafx.javafxcalendarapplication.modules.MonthlyCalendar;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Arrays;
import java.util.Scanner;

public class CalendarController {

    MonthlyCalendar monthlyCalendar;
    String[] months;
    String month;
    int year;

    @FXML
    private ImageView imageUser;

    @FXML
    private Label monthInd;

    @FXML
    private Button nextMonthBtn;

    @FXML
    private Button prevMonthBtn;

    @FXML
    private GridPane root;

    @FXML
    public Button signIn;

    String btnUserStyle;

    /*public void changeUserName(String login) {
        System.out.println(login);
        signIn = new Button();
        signIn.setStyle(btnUserStyle);
        signIn.setText(login);
        signIn.setDisable(true);
    }*/

    @FXML
    private void openWindowSignInOrUp() {
        signIn.setOnMouseClicked((event) -> {
            FXMLLoader loaderIn = new FXMLLoader();
            loaderIn.setLocation(getClass().getResource("in-window.fxml"));
            try {
                loaderIn.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent rootIn = loaderIn.getRoot();
            Stage stageIn = new Stage();
            stageIn.setScene(new Scene(rootIn));
            stageIn.show();
        });
    };

    @FXML
    private Button userNotes;

    @FXML
    private void openUserNotes() {
        userNotes.setOnMouseClicked((event) -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("notes-window.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
    };

    @FXML
    private Button userTodo;

    @FXML
    private void openUserTodo() {
        userTodo.setOnMouseClicked((event) -> {
            FXMLLoader loaderTodo = new FXMLLoader();
            loaderTodo.setLocation(getClass().getResource("todo-window.fxml"));
            try {
                loaderTodo.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent rootTodo = loaderTodo.getRoot();
            Stage stageTodo = new Stage();
            stageTodo.setScene(new Scene(rootTodo));
            stageTodo.show();
        });
    };

    @FXML
    private Button userSettings;

    @FXML
    private void openUserSettings() {
        userSettings.setOnMouseClicked((event) -> {
            FXMLLoader loaderSettings = new FXMLLoader();
            loaderSettings.setLocation(getClass().getResource("settings-window.fxml"));
            try {
                loaderSettings.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent rootSettings = loaderSettings.getRoot();
            Stage stageSettings = new Stage();
            stageSettings.setScene(new Scene(rootSettings));
            stageSettings.show();
        });
    };

    @FXML
    private void initialize() {
        monthlyCalendar = new MonthlyCalendar();
        month = monthlyCalendar.getMonth();
        months = monthlyCalendar.getMonths();
        year = monthlyCalendar.getYear();
        root.add(monthlyCalendar, 0, 1);
        monthlyCalendar.setAlignment(Pos.CENTER);
        monthInd.setText(month + " - " + year);
    }

    @FXML
    private void onPrevBtnClicked() {
        root.getChildren().remove(monthlyCalendar);
        String currentMonth = monthlyCalendar.getMonth();
        if (month.equals(months[0])){
            month = months[months.length - 1];
            --year;
        } else {
            month = months[Arrays.asList(months).indexOf(currentMonth) - 1];
        }
        monthlyCalendar = new MonthlyCalendar(month, year);
        root.add(monthlyCalendar, 0, 1);
        monthlyCalendar.setAlignment(Pos.CENTER);
        monthInd.setText(month + " - " + year);
    }

    @FXML
    private void onNextBtnClicked() {
        root.getChildren().remove(monthlyCalendar);
        String currentMonth = monthlyCalendar.getMonth();
        if (month.equals(months[months.length - 1])){
            month = months[0];
            ++year;
        } else {
            month = months[Arrays.asList(months).indexOf(currentMonth) + 1];
        }
        monthlyCalendar = new MonthlyCalendar(month, year);
        root.add(monthlyCalendar, 0, 1);
        monthlyCalendar.setAlignment(Pos.CENTER);
        monthInd.setText(month + " - " + year);
    }
}