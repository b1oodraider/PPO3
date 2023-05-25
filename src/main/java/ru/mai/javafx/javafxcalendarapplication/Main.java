package ru.mai.javafx.javafxcalendarapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.mai.javafx.javafxcalendarapplication.modules.Const;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        exitFromAccount();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("YourCalendar");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void exitFromAccount() throws FileNotFoundException {
        File file = new File(Const.FILE_USER_ID_PATH);
        PrintWriter printWriter = new PrintWriter(file);

        try (BufferedWriter bf = Files.newBufferedWriter(Path.of(Const.FILE_USER_ID_PATH),
                StandardOpenOption.TRUNCATE_EXISTING)) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        printWriter.println(0);
        printWriter.close();
    }
}