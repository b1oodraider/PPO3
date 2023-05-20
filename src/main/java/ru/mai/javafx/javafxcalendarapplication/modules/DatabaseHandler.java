package ru.mai.javafx.javafxcalendarapplication.modules;

import ru.mai.javafx.javafxcalendarapplication.Plan;
import ru.mai.javafx.javafxcalendarapplication.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.security.MessageDigest;
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

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";

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

    public ResultSet getNote(Plan plan) {
        ResultSet resultSet = null;
        String select = "SELECT note FROM " + Const.NOTE_TABLE +
                " WHERE " + Const.USERS_ID + "=? AND " + Const.NOTES_DATE + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setInt(1, plan.getId_user());

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultSet;
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

    public ResultSet getPlan(Plan plan) { // ПОЛУЧЕНИЕ ЗАДАЧИ ИЗ БД ПО ДАТЕ
        ResultSet resultSet = null;
        String select = "SELECT plan FROM " + Const.PLANS_TABLE +
                " WHERE " + Const.USERS_ID + "=? AND " + Const.PLANS_DEADLINE + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setInt(1, plan.getId_user());

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultSet;
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

    public ResultSet getHoliday(Plan plan) { // ПОЛУЧЕНИЕ ПРАЗДНИКА ИЗ БД ПО ДАТЕ
        ResultSet resultSet = null;
        String select = "SELECT holiday FROM " + Const.HOLIDAYS_TABLE +
                " WHERE " + Const.USERS_ID + "=? AND " + Const.HOLIDAYS_DATE + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setInt(1, plan.getId_user());

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }
}

