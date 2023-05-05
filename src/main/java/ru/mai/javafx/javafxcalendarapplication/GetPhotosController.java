package ru.mai.javafx.javafxcalendarapplication;

import java.io.*;
import java.nio.file.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetPhotosController {

    private static final String GET_URL = "https://api.nasa.gov/planetary/apod?api_key=yIFGyr7sOvcg3QuXOh09rAHE7dZKpZIsCZNthFxu&date=";

    /*private static String date = "";

    public GetPhotosController(String date) {
        this.date = date;
    }*/

    public void getPhotos() throws IOException {
        String date = "2023-04-28";
        String data = sendGET(date);
        System.out.println("GET DONE");
        getPic(data, date);
        getExp(data);

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
            path.append(GetPhotosController.class.getResource("pic2023-04-28.png").getPath());
            //path.append("C/JavaTrainingLearning/projectCalendar/src/main/java/ru/mai/javafx/javafxcalendarapplication/picture/pic2023-04-28.png");
            /*path.delete(path.length() - 17, path.length());
            path.deleteCharAt(0);*/
            path.append("pic" + date + getType(matcher.group()));
            try {
                Files.copy(is, new File(path.toString()).toPath());
            } catch (FileAlreadyExistsException e) {
                System.out.println("file already exists");
            }

            System.out.println("file downloaded");
        } else {
            System.out.println("not URL found");
        }
    }
    private void getExp(String data) {
        Pattern pattern = Pattern.compile("\"[A-Z].+?\\.\"");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            final String STR1 = data.substring(matcher.start()+13, matcher.end());
            System.out.println(STR1);
        } else {
            System.out.println("not URL found");
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
}