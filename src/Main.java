import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO
// add printout statement when the list is empty
// put validation where needed

public class Main {
    static public int existingUserID = 0;
    static Scanner scanner = new Scanner(System.in);
    static DBConnection dataBase = new DBConnection();

    public static void main(String[] args) {

        System.out.println("Welcome to TODO manager!");
        char nextTry = 'y';
        int choice = 0;
        while (nextTry =='y') {

            System.out.println("Do you want to login or register a new user?");
            System.out.println("l - login");
            System.out.println("r - register a new user");
            char action = scanner.nextLine().charAt(0);

            if (action == 'l') {
                login();
                break;
            } else if (action == 'r') {
                registerUser();
                break;
            } else {
                System.out.println("Invalid input!");
                System.out.println("Do you want to try again? y/n");
                nextTry = scanner.nextLine().charAt(0);
            }
        }

        printAction();

        while(nextTry == 'y') {

            System.out.println("Enter your choice");
            try {

                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 0:
                        printAction();
                        break;
                    case 1:
                        System.out.println("Enter task name: ");
                        String taskText = scanner.nextLine();

                        System.out.println("Optional step - Do you want to indicate task's due date? (y - yes or n - no)");
                        char indicateDate = scanner.nextLine().charAt(0);
                        String taskDueDate = "";
                        if (indicateDate == 'n') {
                            taskDueDate = null;
                        } else if (indicateDate == 'y') {
                            System.out.println("Enter task due date (YYYY-MM-DD): ");
                            taskDueDate = scanner.nextLine();
                        } else{
                            System.out.println("Invalid input. Try again");
                        }

                        System.out.println("Optional step - Do you want to indicate task's importance? (y - yes or n - no)");
                        char indicateImportance = scanner.nextLine().charAt(0);
                        String taskImportance = "";
                        if (indicateImportance == 'n') {
                            taskImportance = null;
                        } else if (indicateImportance == 'y') {
                            System.out.println("Enter task importance (high/medium/low): ");
                            taskImportance = scanner.nextLine();
                        } else{
                            System.out.println("Invalid input. Try again");
                        }

                        String taskStatus = "In progress";

                        dataBase.addTask(taskText, taskDueDate, taskImportance, taskStatus, existingUserID);
                        break;
                    case 2:
                        System.out.println("Enter task number you want to amend");
                        Map<Integer, Integer> positions = new HashMap<>();
                        positions = dataBase.printUserTasks(existingUserID);

                        int taskNr = scanner.nextInt();

                        System.out.println("Do you want to change task text, task due date or task importance?");
                        System.out.println("t - edit text");
                        System.out.println("d - edit due date");
                        System.out.println("i - edit importance");
                        scanner.nextLine();
                        String editOption = scanner.nextLine();

                        if(editOption.equals("t")){
                            System.out.println("Enter amended task text");
                            String newTaskText = scanner.nextLine();
                            dataBase.editTaskText(positions.get(taskNr), newTaskText);
                        }else if (editOption.equals("d")){
                            System.out.println("Enter the new task date (YYYY-MM-DD): ");
                            String dueDate = scanner.nextLine();
                            dataBase.editDueDate(positions.get(taskNr), dueDate);
                        }else if (editOption.equals("i")){
                            System.out.println("Enter the new task importance (high/medium/low):");
                            String newTaskImportance = scanner.nextLine();
                            dataBase.editTaskImportance(positions.get(taskNr), newTaskImportance);
                        }else {
                            System.out.println("Invalid input!");
                        }
                        break;
                    case 3:
                        System.out.println("Do you want to print tasks based on their importance?\nPress y - yes or n - no");
                        char printByImportance = scanner.nextLine().charAt(0);
                        if (printByImportance == 'n') {
                            dataBase.printUserTasks(existingUserID);
                        } else if (printByImportance == 'y') {
                            dataBase.printTasksByImportance(existingUserID);
                        } else{
                            System.out.println("Invalid input. Try again");
                        }
                        break;
                        // add printout statement when the list is empty
                    case 4:
                        //mark as done
                        break;
                    case 5:
                        positions = dataBase.printUserTasks(existingUserID);
                        System.out.println("Please enter task ID you want to delete");
                        dataBase.removeTask(positions.get(scanner.nextInt()));
                        break;
                    case 6:
                        System.out.println("Are you sure you want to delete all tasks?\nPress y - yes or n - no");
                        char deleteTasks = scanner.nextLine().charAt(0);
                        if(deleteTasks == 'y'){
                            dataBase.deleteAllTasks(existingUserID);
                        } else if(deleteTasks == 'n'){
                            break;
                        } else{
                            System.out.println("Invalid input. Try again");
                        }
                        break;
                    case 7:
                        nextTry = 'n';
                        break;
                    default:
                        System.out.println("Input not valid (0-7)");
                        break;
                }


            } catch (InputMismatchException e) {
                System.err.println("Wrong input, Integers only");
                scanner.nextLine();
            }
        }
    }
    public static void login () {
        User existingUser = new User();

        System.out.println("Enter username");
        existingUser.setUsername(scanner.nextLine());

        System.out.println("Enter password");
        existingUser.setPsw(scanner.nextLine());

        // method checkUser
        int userID = dataBase.checkUser(existingUser.getUsername(),existingUser.getPsw());

        if (userID > 0) {
            System.out.println("You have logged in successfully!");
            existingUserID = userID;
        }
    }
    public static void registerUser (){
        User newUser = new User();
        System.out.println("Enter username");
        newUser.setUsername(scanner.nextLine());

        Pattern pattern = Pattern.compile("[a-zA-Z0-9]{5,50}");
        Matcher matcher = pattern.matcher(newUser.getUsername());
        while (matcher.matches()== false) {
            System.out.println("Please enter a valid username! It should be at least 5 characters!");
            newUser.setUsername(scanner.nextLine());
            pattern = Pattern.compile("[a-zA-Z0-9]{5,50}");
            matcher = pattern.matcher(newUser.getUsername());
        }
        // Database checking if username exists
        int userID = dataBase.validateUserName(newUser.getUsername());

        while (userID > 0 ) {
            System.out.println("Username already exists! Try again!");
            newUser.setUsername(scanner.nextLine());
            pattern = Pattern.compile("[a-zA-Z0-9+_.!-]]{5,50}");
            matcher = pattern.matcher(newUser.getUsername());
            while (matcher.matches() == false) {

                System.out.println("Please enter a valid username! It should be at least 5 characters!");
                newUser.setUsername(scanner.nextLine());
                pattern = Pattern.compile("[a-zA-Z0-9+_.!-]]{5,50}");
                matcher = pattern.matcher(newUser.getUsername());
            }
            userID = dataBase.validateUserName(newUser.getUsername());
        }

        System.out.println("Enter password");
        newUser.setPsw(scanner.nextLine());

        pattern = Pattern.compile("[a-zA-Z0-9+_.!-]{5,50}");
        matcher = pattern.matcher(newUser.getPsw());
        while (matcher.matches()== false) {
            System.out.println("Please enter a valid password! It should be at least 5 characters!");
            newUser.setPsw(scanner.nextLine());
            pattern = Pattern.compile("[a-zA-Z0-9+_.!-]{5,50}");
            matcher = pattern.matcher(newUser.getPsw());
        }

        System.out.println("Enter full name");
        newUser.setFullName(scanner.nextLine());

        pattern = Pattern.compile("^([A-Za-z]*((\\s)))+[A-Za-z]*$");
        matcher = pattern.matcher(newUser.getFullName());
        while (matcher.matches()== false) {
            System.out.println("Please enter valid first name and last name!");
            newUser.setFullName(scanner.nextLine());
            pattern = Pattern.compile("^([A-Za-z]*((\\s)))+[A-Za-z]*$");
            matcher = pattern.matcher(newUser.getFullName());
        }

        existingUserID = dataBase.createUser(newUser.getUsername(), newUser.getPsw(), newUser.getFullName());

        if (existingUserID>0){
            System.out.println("You have registered an account!");
        }
    }
    public static void printAction(){
        System.out.println("\nPress");
        System.out.println("\t 0 - To print choice options");
        System.out.println("\t 1 - To add a task");
        System.out.println("\t 2 - To edit a task");
        System.out.println("\t 3 - To view all tasks");
        System.out.println("\t 4 - To mark task as done");
        System.out.println("\t 5 - To remove a task");
        System.out.println("\t 6 - To delete all tasks");
        System.out.println("\t 7 - To quit the application");
    }

}


