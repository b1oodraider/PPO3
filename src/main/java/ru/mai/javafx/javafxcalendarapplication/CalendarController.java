package ru.mai.javafx.javafxcalendarapplication;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import ru.mai.javafx.javafxcalendarapplication.modules.MonthlyCalendar;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
    private GridPane root;

    @FXML
    private Button signIn;

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
        month = monthlyCalendar.getMonth();
        months = monthlyCalendar.getMonths();
        year = monthlyCalendar.getYear();
        root.add(monthlyCalendar, 0, 1);
        monthlyCalendar.setAlignment(Pos.CENTER);
        monthInd.setText(month + " - " + year);
        month = monthlyCalendar.getMonth();
        year = monthlyCalendar.getYear();
        monthInd.setText(month + " - " + year);
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
            showNotificationAboutNotSignIn();
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
        } else {
            showNotificationAboutNotSignIn();
        }
    }


    @FXML
    void userExit() {
        if (checkLogin()) {
            File file = new File("src/main/resources/userID.txt");
            try {
                PrintWriter printWriter = new PrintWriter(file);
                try (BufferedWriter bf = Files.newBufferedWriter(Path.of("src/main/resources/userID.txt"),
                        StandardOpenOption.TRUNCATE_EXISTING)) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
                printWriter.println(0);
                printWriter.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            showNotificationAboutLogOut();
        } else {
            showNotificationAboutLogOutIsNot();
        }
    }

    private void showNotificationAboutLogOut() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Log out");
        alert.setHeaderText("You log out! Please sign in or up!");

        alert.showAndWait();
    }

    private void showNotificationAboutLogOutIsNot() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Can't Log out");
        alert.setHeaderText("You can't log out because you don't sign in");

        alert.showAndWait();
    }

    private void showNotificationAboutNotSignIn() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Not full version");
        alert.setHeaderText("You can't use full version because you don't sign in");

        alert.showAndWait();
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