package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {
    public static void main(String[] args) {

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/finalproject", "root", "Marsik565!");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users");

            while (rs.next()){
                System.out.printf("ID %d username:  %s password: %s full name: %s \n",
                        rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
            }

            con.close();

        }catch (Exception e){
            System.out.println(e);
        }


    }
}
