package ru.mai.javafx.javafxcalendarapplication.modules;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ru.mai.javafx.javafxcalendarapplication.GetPhotosController;

import java.io.*;
import java.util.*;

public class MonthlyCalendar extends GridPane {
    private double sizeX;
    private double sizeY;
    private String month;
    private int year;
    String[] months;

    public MonthlyCalendar(String month, int year) {
        this.sizeX = 357;
        this.sizeY = 350;
        this.month = month;
        this.year = year;

        fillContent();
    }

    public MonthlyCalendar() {
        Calendar calendar = Calendar.getInstance();

        this.sizeX = 357;
        this.sizeY = 350;
        this.month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("en"));
        this.year = calendar.get(Calendar.YEAR);

        fillContent();
    }

    private void fillContent() {
        months = new String[] {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        String[] weekDays = new String[]{"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Arrays.asList(months).indexOf(month), 1);
        ArrayList<Button> buttons = new ArrayList<>();

        for (int i = 0; i < weekDays.length; ++i) {
            Label label = new Label(weekDays[i]);
            label.setPrefSize(sizeX / 3, sizeY / 5);
            label.setAlignment(Pos.CENTER);
            label.setFont(Font.font(15));

            if (i > 4) {
                label.setTextFill(Color.RED);
            }

            this.add(label, i, 0);
        }

        for (int i = 1; i < 7; ++i) {
            for (int j = 0; j < weekDays.length; ++j) {
                Button button = new Button();
                button.setCursor(Cursor.HAND);
                button.setPrefSize(sizeX / 3, sizeY / 5);

                if (j > 4) {
                    button.setTextFill(Color.RED);
                    button.setStyle("-fx-background-color: #ffe2e2; -fx-border-color: #f48a8a");
                }

                this.add(button, j, i);
                buttons.add(button);
            }
        }

        int count = 0;
        int countOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int numberOfDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (numberOfDayOfWeek == 1) {
            numberOfDayOfWeek += 5;
        } else {
            numberOfDayOfWeek -= 2;
        }

        calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        String currentMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("en"));
        int currentYear = calendar.get(Calendar.YEAR);


        String[] dates;
        String[] holidays;

        try {
            List<String> listOfDates = new ArrayList<String>();
            List<String> listOfHolidays = new ArrayList<String>();
            BufferedReader bf = new BufferedReader(new FileReader("Holidays.txt"));
            String line = bf.readLine();
            while(line != null) {
                listOfDates.add(line.split("/")[0]);
                listOfHolidays.add(line.split("/")[1]);
                line = bf.readLine();
            }
            bf.close();

            dates = listOfDates.toArray(new String[0]);
            holidays = listOfHolidays.toArray(new String[0]);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        int day;
        String dateOfDay = "";
        String dateOfBtn = "";
        boolean isContains = false;
        int whatIsDate = 0;
        for (Button btn : buttons) {
            if (count >= numberOfDayOfWeek && count < countOfDays + numberOfDayOfWeek) {
                day = count - numberOfDayOfWeek + 1;
                dateOfBtn = String.valueOf(year) + "-" + String.valueOf(Arrays.asList(months).indexOf(month) + 1) + "-" + String.valueOf(day) ;
                btn.setId(dateOfBtn);
                btn.setOnMouseClicked((event) -> {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        GetPhotosController gtf = new GetPhotosController();
                        String description = "";
                        try {
                            gtf.getPhotos(btn.getId().toString());
                            description = gtf.getDescription();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Label label = new Label();
                        label.setText(description);
                        label.setMaxWidth(1200);
                        label.setMaxHeight(100);
                        label.setLayoutY(700);
                        Image image = new Image(MonthlyCalendar.class.getResourceAsStream("/pictures/pic" + btn.getId().toString() + ".jpg"));
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(600);
                        imageView.setFitWidth(900);
                        //imageView.setLayoutX(100);
                        imageView.setLayoutY(100);
                        Group root = new Group(label, imageView);
                        Scene scene = new Scene(root);
                        Stage stagePhotos = new Stage();
                        stagePhotos.setWidth(1200);
                        stagePhotos.setHeight(800);
                        stagePhotos.setTitle("This day's photo");
                        stagePhotos.setScene(scene);
                        stagePhotos.show();
                    } else {
                        Group root = new Group();
                        Scene scene = new Scene(root);
                        Stage stagePhotos = new Stage();
                        stagePhotos.setWidth(500);
                        stagePhotos.setHeight(500);
                        stagePhotos.setTitle("This day's notes");
                        stagePhotos.setScene(scene);
                        stagePhotos.show();
                    }
                });
                dateOfDay = String.valueOf(day) + "-" + String.valueOf(Arrays.asList(months).indexOf(month) + 1);
                for (int i = 0; i < dates.length; ++i) {
                    if (dateOfDay.equals(dates[i])) {
                        isContains = true;
                        whatIsDate = i;
                    }
                }
                if (isContains) {
                    btn.setText(day + "\n" + holidays[whatIsDate]);
                    btn.setFont(new Font(8));
                    btn.setStyle("-fx-background-color: #ffe2e2; -fx-border-color: #f48a8a; -fx-text-alignment: CENTER");
                } else {
                    btn.setText(String.valueOf(day));
                }
                isContains = false;
                if (currentDay == day && currentYear == year && currentMonth.equals(month)) {
                    btn.setStyle(btn.getStyle() + "; -fx-border-color: black");
                }
            } else {
                btn.setDisable(true);
            }
            ++count;
        }
    }

    public String getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String[] getMonths() {
        return months;
    }
}

