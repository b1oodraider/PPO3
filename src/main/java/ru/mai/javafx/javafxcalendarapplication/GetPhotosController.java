package ru.mai.javafx.javafxcalendarapplication;

import javafx.scene.control.Alert;

import java.io.*;
import java.nio.file.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetPhotosController {

    private static final String GET_URL = "https://api.nasa.gov/planetary/apod?api_key=yIFGyr7sOvcg3QuXOh09rAHE7dZKpZIsCZNthFxu&date=";

    private String description;
    public boolean isPhotoHere;

    public void getPhotos(String date) throws IOException {
        String data = sendGET(date);
        System.out.println("GET DONE");
        getPic(data, date);
        this.description = getExp(data);
    }

    public String getDescription() {
        return description;
    }

    private String sendGET(String date) throws IOException {
        final String sb = GET_URL + date;
        URL obj = new URL(sb);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response);
            return response.toString();
        } else {
            return ("GET not");
        }
    }

    private void getPic(String data, String date) throws IOException {
        Pattern pattern = Pattern.compile("https.+?\\..{2,3}g");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            final String STR1 = matcher.group();
            URL obj = new URL(STR1);
            InputStream is = obj.openStream();
            StringBuilder path = new StringBuilder();
            path.append(GetPhotosController.class.getResource("/pictures/date.txt"));
            path.delete(path.length() - 8, path.length());
            path.delete(0, 5);
            path.append("/pic" + date + getType(matcher.group()));
            try {
                Files.copy(is, new File(path.toString()).toPath());
            } catch (FileAlreadyExistsException e) {
                System.out.println("file already exists");
            }
            System.out.println("file downloaded");
            isPhotoHere = true;
        } else {
            isPhotoHere = false;
            showNotificationAboutNotPhoto();
        }
    }
    private String getExp(String data) {
        Pattern pattern = Pattern.compile("n\":\"[A-Z].+?\"");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return (data.substring(matcher.start() + 4, matcher.end() - 1));
        } else {
            return ("This Photo has not description");
        }
    }

    private static String getType(String url) {
        Pattern pattern = Pattern.compile("\\..{2,3}?g");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            String fType = new String(matcher.group());
            return fType;
        } else {
            return "no types detected";
        }
    }

    private void showNotificationAboutNotPhoto() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Photo isn't");
        alert.setHeaderText("There isn't photo this day");
        alert.showAndWait();
    }
}