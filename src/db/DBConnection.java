package db;

import java.sql.*;
import java.util.Scanner;

public class DBConnection {

    private final String dbURL = "jdbc:mysql://localhost:3306/finalproject";
    private final String dbUser = "root";
    private final String dbPsw = "Marsik565!";

    /*    public void createUser(String userName,String password,String fullName) {
            try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {



            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

    public int checkUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPsw)) {
            String sql = "SELECT * FROM Users WHERE username ='" + username + "' and password ='" + password + "'";

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {

                //returns Users ID Nr.
                return resultSet.getInt(1);
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int createUser(String username, String password, String fullName) {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPsw)) {
            String sql = "INSERT INTO Users (username, password, fullname) VALUES (?,?,?)";


            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, fullName);
            preparedStatement.executeUpdate();

            String sqlID = "SELECT * FROM Users WHERE username ='" + username + "' and password ='" + password + "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlID);

            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }


        } catch (SQLException e) {
            e.printStackTrace();

        }

        return 0;
    }


    public int validateUserName(String username){

        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPsw)) {
            String sqlUser = "SELECT * FROM Users WHERE username ='" + username + "'";

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlUser);

            if (resultSet.next()) {

                //returns Users ID Nr.
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
