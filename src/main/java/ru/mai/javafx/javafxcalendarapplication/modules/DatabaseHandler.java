package ru.mai.javafx.javafxcalendarapplication.modules;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.security.MessageDigest;
import java.util.Scanner;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws SQLException {
        String connectionString = "jdbc:mysql://db4free.net:3306/users_info";
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public void sighUpUser(User user) {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USERS_FIRSTNAME + "," + Const.USERS_USERNAME + "," +
                Const.USERS_PASSWORD + ")" + "VALUES(?,?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, ConvertPasswordToSha256(user.getPassword()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getUser(User user) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE +
                " WHERE " + Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, ConvertPasswordToSha256(user.getPassword()));

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static String ConvertPasswordToSha256(String password) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(password.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public void makeNote(Plan plan) {
        String insert = "INSERT INTO " + Const.NOTE_TABLE + "(" + Const.USERS_ID + "," +
                Const.NOTES_DATE + "," + Const.NOTES_NOTE + ")" + "VALUES(?,?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setInt(1, plan.getId_user());
            preparedStatement.setString(2, plan.getDate());
            preparedStatement.setString(3, plan.getNote());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void makePlan(Plan plan) {
        String insert = "INSERT INTO " + Const.PLANS_TABLE + "(" + Const.USERS_ID + "," +
                Const.PLANS_DEADLINE + "," + Const.PLANS_PLAN + ")" + "VALUES(?,?,?)";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setInt(1, plan.getId_user());
            preparedStatement.setString(2, plan.getDate());
            preparedStatement.setString(3, plan.getNote());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void makeHoliday(Plan plan) {
        String insert = "INSERT INTO " + Const.HOLIDAYS_TABLE + "(" + Const.USERS_ID + "," +
                Const.HOLIDAYS_DATE + "," + Const.HOLIDAYS_HOLIDAY + ")" + "VALUES(?,?,?)";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setInt(1, plan.getId_user());
            preparedStatement.setString(2, plan.getDate());
            preparedStatement.setString(3, plan.getNote());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getNote() {
        ResultSet resultSet = null;

        String select = "SELECT note " +
                "FROM `notes` " +
                "JOIN users ON notes.id_user = users.id_user " +
                "WHERE notes.date" + "=? AND " + "users.id_user" + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);

            preparedStatement.setString(1, readDateFromFile(Const.FILE_DATE_PATH));
            preparedStatement.setInt(2, readIdFromFile(Const.FILE_USER_ID_PATH));
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getPlan() {
        ResultSet resultSet = null;

        String select = "SELECT plan " +
                "FROM `plans` " +
                "JOIN users ON plans.id_user = users.id_user " +
                "WHERE plans.date" + "=? AND " + "users.id_user" + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);

            preparedStatement.setString(1, readDateFromFile(Const.FILE_DATE_PATH));
            preparedStatement.setInt(2, readIdFromFile(Const.FILE_USER_ID_PATH));
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getHoliday() {
        ResultSet resultSet = null;

        String select = "SELECT holiday " +
                "FROM `holidays` " +
                "JOIN users ON holidays.id_user = users.id_user " +
                "WHERE holidays.date" + "=? AND " + "users.id_user" + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);

            preparedStatement.setString(1, readDateFromFile(Const.FILE_DATE_PATH));
            preparedStatement.setInt(2, readIdFromFile(Const.FILE_USER_ID_PATH));
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public String readDateFromFile(String path) {
        String date = "";
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            date = scanner.nextLine();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String finalDate = date;
        return finalDate;
    }

    public int readIdFromFile(String path) {
        int id = 0;
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            id = scanner.nextInt();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int finalId = id;
        return finalId;
    }
}

