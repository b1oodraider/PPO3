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
    private Button btnExit;

    @FXML
    private ImageView imageUser;

    @FXML
    private Label monthInd;

    @FXML
    private Button nextMonthBtn;

    @FXML
    private Button prevMonthBtn;

    @FXML
    public GridPane root;

    @FXML
    public Button signIn;

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
    public void initialize() {
        monthlyCalendar = new MonthlyCalendar();
        if (checkLogin()) {
            month = monthlyCalendar.getMonth();
            months = monthlyCalendar.getMonths();
            year = monthlyCalendar.getYear();
            root.add(monthlyCalendar, 0, 1);
            monthlyCalendar.setAlignment(Pos.CENTER);
            monthInd.setText(month + " - " + year);
        } else {
            month = monthlyCalendar.getMonth();
            year = monthlyCalendar.getYear();
            monthInd.setText(month + " - " + year);
        }
    }

    @FXML
    private void onPrevBtnClicked() {
        if (checkLogin()) {
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
        } else {
            System.out.print("Please login to use full version");
        }
    }

    @FXML
    private void onNextBtnClicked() {
        if (checkLogin()) {
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


    @FXML
    void userExit() {
    }

    public boolean checkLogin(){
        File file = new File("src/main/resources/userID.txt");
        try {
            Scanner scanner = new Scanner(file);
            if (scanner.nextInt() != 0) {
                return true;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}