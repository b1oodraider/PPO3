package ru.mai.javafx.javafxcalendarapplication.modules;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.*;

public class MonthlyCalendar extends GridPane {
    private double sizeX;
    private double sizeY;
    private String month;
    private int year;
    String[] months;

    public MonthlyCalendar(String month, int year) {
        this.sizeX = 300;
        this.sizeY = 300;
        this.month = month;
        this.year = year;

        fillContent();
    }

    public MonthlyCalendar() {
        Calendar calendar = Calendar.getInstance();

        this.sizeX = 300;
        this.sizeY = 300;
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
            label.setPrefSize(sizeX / 5, sizeY / 6);
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
                button.setPrefSize(sizeX / 5, sizeY / 6);

                if (j > 4) {
                    button.setTextFill(Color.RED);
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

        for (Button btn : buttons) {
            if (count >= numberOfDayOfWeek && count < countOfDays + numberOfDayOfWeek) {
                btn.setText(String.valueOf(count - numberOfDayOfWeek + 1));
                btn.setCursor(Cursor.HAND);
                if (currentDay == count - numberOfDayOfWeek + 1 && currentYear == year && currentMonth.equals(month)) {
                    btn.setStyle("-fx-border-color: black");
                }
                /*btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton button = event.getButton();
                        if (button == MouseButton.PRIMARY) {

                        } else if(button==MouseButton.SECONDARY){
                            label.setText("SECONDARY button clicked on button");
                        }
                    }
                });*/
            } else {
                btn.setDisable(true);
            }
            ++count;
        }
    }

    public double getSizeX() {
        return sizeX;
    }

    public void setSizeX(double sizeX) {
        this.sizeX = sizeX;
    }

    public double getSizeY() {
        return sizeY;
    }

    public void setSizeY(double sizeY) {
        this.sizeY = sizeY;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String[] getMonths() {
        return months;
    }
}

