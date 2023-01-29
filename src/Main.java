import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
                        //add task
                        break;
                    case 2:
                        //edit task
                        break;
                    case 3:
                        //print all tasks (includes sorting)
                        break;
                    case 4:
                        //mark as done
                        break;
                    case 5:
                        //remove task
                        break;
                    case 6:
                        //delete all tasks
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
