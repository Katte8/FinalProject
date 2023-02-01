import java.io.Serializable;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {

    private final String dbURL = "jdbc:mysql://localhost:3306/finalproject"; //dont forget to change!!
    private final String dbUser = "root";
    private final String dbPsw = "Marsik565!"; //dont forget to change!!

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
    public int checkTaskList(int userId){
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPsw)) {
            String sql = "SELECT * FROM TaskList WHERE userID = '" + userId + "';";

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return 1;
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int checkNumberOfTasks(int userId){
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPsw)) {
            String sql = "SELECT COUNT(taskID) FROM TaskList WHERE userID = '" + userId + "';";

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

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
    public void addTask(String taskText, String taskDueDate, String taskImportance, String taskStatus, int userID) {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPsw)) {

            String sql = "INSERT INTO tasklist (tasktext, taskduedate, taskimportance, taskstatus, userID) VALUES (?,?,?,?,?);";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, taskText);
            preparedStatement.setString(2, taskDueDate);
            preparedStatement.setString(3, taskImportance);
            preparedStatement.setString(4, taskStatus);
            preparedStatement.setInt(5, userID);

            int rowInserted = preparedStatement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new task was inserted successfully");
            } else {
                System.out.println("Something went wrong");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Map<Integer,Integer> printUserTasks(int userId) {
        Map<Integer, Integer> positions = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPsw)) {

            String sql = "SELECT * FROM TaskList WHERE userID ='" + userId + "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);



            int count = 0;

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String taskText = resultSet.getString("tasktext");
                String dueDate = (resultSet.getString("taskduedate") == null) ? "Not indicated" : resultSet.getString("taskduedate");
                String taskImportance = (resultSet.getString("taskimportance") == null) ? "Not indicated" : resultSet.getString("taskimportance");
                String taskStatus = resultSet.getString("taskstatus");

                String output = "Task #%d: %s - Deadline: %s - Importance: %s - Status: %s";
                System.out.println(String.format(output, ++count, taskText, dueDate, taskImportance, taskStatus));
                positions.put(count, id);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positions;
    }
    public void printTasksByImportance(int userId){
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPsw)) {
            String sql = "SELECT * FROM TaskList WHERE userID ='" + userId + "' ORDER BY CASE WHEN taskimportance = 'high' THEN 1 WHEN taskimportance = 'medium' THEN 2 WHEN taskimportance = 'low' THEN 3 ELSE 4 END";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            int count = 0;

            //repetitive code
            while (resultSet.next()) {
                String taskText = resultSet.getString("tasktext");
                String dueDate = (resultSet.getString("taskduedate") == null) ? "Not indicated" : resultSet.getString("taskduedate");
                String taskImportance = (resultSet.getString("taskimportance") == null) ? "Not indicated" : resultSet.getString("taskimportance");
                String taskStatus = resultSet.getString("taskstatus");

                String output = "Task #%d: %s - Deadline: %s - Importance: %s - Status: %s";
                System.out.println(String.format(output, ++count, taskText, dueDate, taskImportance, taskStatus));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void editTaskText (int taskID, String taskText){
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPsw) ){
            String sql = "UPDATE TaskList SET tasktext = ? WHERE taskID = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,taskText);
            preparedStatement.setInt(2, taskID);
            preparedStatement.executeUpdate();
            System.out.println("TODO task text updated successfully.");

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void editDueDate (int taskID, String dueDate){

        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPsw) ){
            String sql = "UPDATE TaskList SET taskduedate = ? WHERE taskID = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, dueDate);
            preparedStatement.setInt(2, taskID);
            preparedStatement.executeUpdate();
            System.out.println("TODO task due date updated successfully!");


        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public void editTaskImportance (int taskID, String taskImportance){

        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPsw) ){
            String sql = "UPDATE TaskList SET taskimportance = ? WHERE taskID = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,taskImportance);
            preparedStatement.setInt(2, taskID);
            preparedStatement.executeUpdate();
            System.out.println("TODO task importance updated successfully!");

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeTask(int taskID){
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPsw)) {
            String sql = "DELETE FROM TaskList WHERE taskID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,taskID);
            preparedStatement.executeUpdate();

            System.out.println("The task was deleted");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllTasks(int userId){
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPsw)) {
            String sql = "DELETE FROM TaskList WHERE userID = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.executeUpdate();

            System.out.println("The tasks were deleted");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void markAsDone(int taskID){
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPsw)) {
            String sql = "UPDATE TaskList SET taskstatus = 'Done' WHERE taskID = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,taskID);
            preparedStatement.executeUpdate();

            System.out.println("The task status was changed");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
