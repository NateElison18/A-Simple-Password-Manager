/**
 * Author: Nate Elison
 * Date: 5/10/23
 *
 * This program allows users to save account usernames and passwords. The info is saved to a txt file.
 * The program can be run again, the user can login and find those accounts. 
 * They can display all accounts on file, add, modify, and remove accounts. 
 * Users can also be removed. The txt file is encrypted and needs the program to decrypt the info to present in readable form.
 * Multiple users can add accounts to the txt file and those accounts can only be accessed by the user.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;




public class OOP1_FinalProject_v2 {
    public static void main(String[] args) throws FileNotFoundException {
        runProgram();
    }
    
    /**
     * This method is used to run the program. It combines and loops the readFile, runWelcomeMenu, runMainMenu, and writeFile.  
     * 
     * @param file (File; an object that references a text file, where the user and account info is stored. 
     * @param accounts (ArrayList<AccountsV2>; an arraylist of AccountsV2 objects)
     * @param welcomeMenuOptions (int; the amount of options in the welcome menu)
     * @param currentUser (UsersV2; Object with info that will be passed along to continue the program)
     * @param runMain (boolean; Returned by runMainMenu, used to decide if the main menu should be run again or if the program should return to the welcome menu.)
     * 
     * @throws FileNotFoundException
     */
    static void runProgram() throws FileNotFoundException {

        try {
            File file = new File("accounts.txt");

            ArrayList<AccountsV2> accounts = readFile(file);

            // Keep program running
            while (true) {
                // Run welcome menu until a successful login or user creation
                UsersV2 currentUser = runWelcomeMenu(accounts);
                // Check for an exit command
                while (currentUser.getUsername().equals("%!%") && currentUser.getPassword().equals("%!%")) {
                    currentUser = runWelcomeMenu(accounts);
                }

                //Run main menu until user is logged out or deleted.
                boolean runMain = true;
                while (runMain) {
                    runMain = runMainMenu(currentUser, accounts);
                    writeFile(file, accounts);
                    accounts = readFile(file);

                }
            }
        }
        //If file dosen't exist yet, create the first user so that there is a file created and initialized to run the rest of the program. 
        catch (FileNotFoundException e){
            System.out.println("Welcome! Looks like this is your first time running A Simple Password Manager.");
            System.out.println("Would you like to create a new user? (y/n)");
            Scanner input = new Scanner(System.in);
            String answer = input.next();
            if (answer.equalsIgnoreCase("y")){
                addFirstUser();
                runProgram();
            }
            else {
                System.out.println("Exiting the program.");
                return;
            }

        }

    }

    // Menu Methods
    
    /**
     * This method displays the welcome menu
     * 
     */
    static void displayWelcomeMenu() {
        System.out.println("A Simple Password Manager");
        System.out.println("-------------------------");
        System.out.println("\t1) Create new user \n\t"
                + "2) Login to existing user");

    }

    /**
     * This method displays the main menu options after the user has been logged in. 
     * 
     * @param user (String; the current logged in user)
     * 
     */
    static void displayMainMenu(String user) {

        System.out.println("\nWelcome " + user + "!");
        System.out.println("-----------------------");
        System.out.println("\t1) Add account \n\t"
                + "2) Display all identifiers \n\t"
                + "3) Display Account info \n\t"
                + "4) Delete Account \n\t"
                + "5) Delete user \n\t"
                + "6) Log out");
    }

    /**
     * This method is used to run the welcome menu and get the user information that will be used in the main menu. 
     * The method either creates a brand new user or creates a user object with new user information.
     * 
     * @param accounts (ArrayList<AccountsV2>; an arraylist of AccountsV2 objects)
     * @param welcomeMenuOptions (int; the amount of options in the welcome menu)
     * @param selection (int; the selection input by the user)
     * 
     * @return currentUser (UsersV2; Object with info that will be passed along to continue the program)
     */
    static UsersV2 runWelcomeMenu(ArrayList<AccountsV2> accounts) {
        displayWelcomeMenu();
        int welcomeMenuOptions = 2;
        int selection = getSelection(welcomeMenuOptions);
        if (selection == 1) {
            UsersV2 currentUser = addUser(accounts);
            return currentUser;
        }
        else {
            UsersV2 currentUser = userLogin(accounts);
            return currentUser;
        }

    }

    /**
     * This method is used to run the main menu with the logged in user.
     * 
     * @param accounts (ArrayList<AccountsV2>; an arraylist of AccountsV2 objects)
     * @param mainMenuOptions (int; the amount of options in the main menu, plugged into getSelection to get the users menu choice.)
     * @param selection (int; the selection input by the user, used to run the option selected)
     * @param currentUser (UsersV2; Object with info that was passed along from the runWelcomeMenu method)
     * @param user (String; the user's name in string form, pulled from currentUser)
     * @param userPassword (String; the user's password in string form, pulled from currentUser)
     * 
     * @return (boolean; The method will return true if it should be run again, and false if it should not)
     *      
     */
    static boolean runMainMenu(UsersV2 currentUser, ArrayList<AccountsV2> accounts)  {
        String user = currentUser.getUsername();
        String userPassword = currentUser.getPassword();
        displayMainMenu(user);
        int mainMenuOptions = 6;
        int selection = getSelection(mainMenuOptions);
        if (selection == 1) {
            addAccount(user, userPassword, accounts);

        }
        else if (selection ==2) {
            displayIdentifiers(user, accounts);

        }
        else if (selection == 3) {
            displayAccount(user, accounts);

        }
        else if (selection == 4) {
            deleteAccount(user, accounts);
        }
        else if (selection == 5) {
            boolean deleteUser = deleteUser(user, accounts);
            return deleteUser;
        }
        else {
            return logout(user);
        }
        return true;

    }

    /**
     * This method is get a selection from the user. It will run until an integer within bounds is entered. 
     * 
     * @param input (Scanner; scans the sytem for the users input)
     * @param options (int; the number of options presented to the user)
     * 
     * @return selection (int; the input integer from the user)
     *      
     */
    static int getSelection(int options) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter selection: ");
        int selection = input.nextInt();

        while (selection > options || selection < 1) {
            System.out.println("Selection out of bounds. Please input a number between 1 and " + options + ":");
            selection = input.nextInt();
        }
        System.out.println();
        return selection;
    }

    
    // Exit Menu method
    static boolean exit(String input) {
        if (input.equalsIgnoreCase("exit")) return true;
        else 							    return false;
    }

    // Welcome menu action methods

    //Create First user
    
    /**
     * This method is used to create the first user. It initializes the text file, so that it can be then passed along to the rest of the program. 
     * It writes the user and userpassword to the txt file along with place holders to not disrupt the pattern of user, userpassword, identifier, username, and password in the txt file. 
     * 
     * @param input (Scanner; Scans the system to get inputs from the user)
     * @param inputUsername (String; the Username input by the user)
     * @param inputPassword (String; the password input by the user)
     * @param output (Printwriter; writes to the text file)
     * @param key (int; key generated by makeKey taking into account the username and password. Is then used to encrypt the username and password to the text file.)
     * @param placeHolderIdentifier (String; placeholder to write in place of where an identifier would normally be written in the file. Since no account info is ready yet to write to the file, something has to be written to not disrupt the text file's pattern.)
     * @param placeHolderUsername (String; placeholder to write in place of where an account would normally be written in the txt file.)
     * @param placeHolderPassword (String; Written in place of where an account password would normally by written in the txt file.)
     * 
     * @throws FileNotFoundException
     */
    static void addFirstUser() throws FileNotFoundException {
        System.out.println("\nCreate new user");
        System.out.println("----------------------");
        System.out.println("Type 'exit' at anytime to go back");
        Scanner input = new Scanner(System.in);

        //Set user username
        System.out.print("Username: ");
        String inputUsername = input.nextLine();
        if (exit(inputUsername)){
            System.out.println("Exiting Program");
            return;
        }

        //Set user password
        System.out.print("Password: ");
        String inputPassword = input.next();
        if (exit(inputPassword)) {
            System.out.println("Exiting the program");
            return;
        }

        // Encrypt user and password, write to .txt file. 
        PrintWriter output = new PrintWriter("Accounts.txt");
        int key = makeKey(inputUsername, inputPassword);
        inputUsername = encryption(inputUsername, key);
        inputPassword = encryption(inputPassword, key);
        String placeholderIdentifier = encryption("placeholderIdentifier", key);
        String placeholderUsername = encryption("placeholderUsername", key);
        String placeholderPassword = encryption("placeholderPassword", key);
        output.println(inputUsername + " " + inputPassword + " " + placeholderIdentifier + " " + placeholderUsername + " " + placeholderPassword + " ");
        output.close();
        System.out.println("Account created successfully. Please now sign in.\n");
    }
    
	/**
	 * This method is used to create a new user and save the user info to the accounts arraylist.  
	 * 
     * @param input (Scanner; Scans the system to get inputs from the user)
	 * @param accounts (ArrayList<AccountsV2>; an arraylist of AccountsV2 objects)
	 * @param inputUsername (String; the Username input by the user)
     * @param inputPassword (String; the password input by the user)
	 * @param checkUser (String; the user saved in an AccountsV2 object that the program checks to see if it matches the inputUsername.)
	 * 
	 * @return failed (UsersV2; object created using UsersV2 default constructor. Returned if the program should run the welcome menu again.)
	 * @return newUser (UsersV2; object created when a user was created successfully with info that can then be passed along into runMainMenu.)
	 */
    // Create user
    static UsersV2 addUser(ArrayList<AccountsV2> accounts) {
        System.out.println("\nCreate new user");
        System.out.println("----------------------");
        System.out.println("Type 'exit' at anytime to go back");
        Scanner input = new Scanner(System.in);

        //Get user username
        System.out.print("Username: ");
        String inputUsername = input.nextLine();
        if (exit(inputUsername)){
            System.out.println("Returning to the welcome menu.\n");
            UsersV2 failed = new UsersV2();
            return failed;
        }

        //Check if user already exists
        for(int i = 0; i < accounts.size(); i++){
            String checkUser = accounts.get(i).user;
            if(inputUsername.equals(checkUser)){
                System.out.println("User " + inputUsername + " already exists. Returning to the welcome menu.\n");
                UsersV2 failed = new UsersV2();
                return failed;
            }
        }
        //Get user password
        System.out.print("Password: ");
        String inputPassword = input.next();
        if (exit(inputPassword)) {
            System.out.println("Returning to the welcome menu\n");
            UsersV2 failed = new UsersV2();
            return failed;
        }

        //Create and return User object
        UsersV2 newUser = new UsersV2(inputUsername, inputPassword);
        return newUser;

    }
    
    //  User login
    /**
     * This method gets a username and password from the user and compares it to users saved. 
     * If it finds a match, it returns the a UsersV2 object created with the credentials, 
     * if not it returns an object created using the default constructor. 
     * 
	 * @param input (Scanner; Scans the system to get inputs from the user)
	 * @param accounts (ArrayList<AccountsV2>; an arraylist of AccountsV2 objects)
	 * @param inputUsername (String; the Username input by the user)
     * @param inputPassword (String; the password input by the user)
	 * @param checkUser (String; the user saved in an AccountsV2 object that the program checks to see if it matches the inputUsername.)
	 * @param checkPassword (String; the password saved in an AccountsV2 object in the accounts array list that the program checks to see if it matches the inputPassword.)
	 *       
	 * @return failed (UsersV2; object created using UsersV2 default constructor. Returned if the program should run the welcome menu again.)
	 * @return currentUser (UsersV2; object created when a user was logged in successfully with info that can then be passed along into runMainMenu.)
	 * 
	 */
    static UsersV2 userLogin(ArrayList<AccountsV2> accounts){
        System.out.println("\nUser Login");
        System.out.println("----------------------");
        System.out.println("Type 'exit' at anytime to go back");
        Scanner input = new Scanner(System.in);

        //Get user username
        System.out.print("Username: ");
        String inputUsername = input.nextLine();
        if (exit(inputUsername)) {
            System.out.println("Returning to the welcome menu.\n");
            UsersV2 failed = new UsersV2();
            return failed;
        }

        //Get user password
        System.out.print("Password: ");
        String inputPassword = input.next();
        if (exit(inputPassword)) {
            System.out.println("Returning to the welcome menu.\n");
            UsersV2 failed = new UsersV2();
            return failed;
        }

        for (int i = 0; i < accounts.size(); i++){
            String checkUser = accounts.get(i).getUser();
            if(inputUsername.equals(checkUser)){
                String checkPassword = accounts.get(i).getUserPassword();
                if(inputPassword.equals(checkPassword)){
                    UsersV2 currentUser = new UsersV2(inputUsername, inputPassword);
                    System.out.println("Login successful!");
                    return currentUser;
                }
            }
        }
        System.out.println("Invalid username and/or password. Returning to menu.\n");
        UsersV2 failed = new UsersV2();
        return failed;
    }



    // Main menu action methods

    //  Add account
    /**
     * This method gets new account info from the user and saves it as an AccountsV2 object to the arrayList accounts. 
     * If the account already exists, it allows the user to modify the accounts info that will then be saved to the arraylist.
     * 
	 * @param input (Scanner; Scans the system to get inputs from the user)
	 * @param accounts (ArrayList<AccountsV2>; an arraylist of AccountsV2 objects)
	 * @param user (String; the user the account will be added under)
	 * @param userPassword (String; the password to the user the account will be added under)
	 * @param inputIdentifier (String; the identifier to the account that will be added)
	 * @param inputUsername (String; the account Username input by the user)
     * @param inputPassword (String; the account password input by the user)
	 * @param checkUser (String; the user saved in an AccountsV2 object that the program looks for to find a duplicate identifier.)
	 * @param checkIdentifier (String; the identifier saved in an AccountsV2 object in accounts that the program looks for to find a duplicate.)
	 *       
	 * 
	 */
    static void addAccount(String user, String userPassword, ArrayList<AccountsV2> accounts) {
        System.out.println("Add new account");
        System.out.println("----------------------");
        System.out.println("Type 'exit' at anytime to go back");
        Scanner input = new Scanner(System.in);

        System.out.print("Account identifier (ex. Google, AppleID, Microsoft, etc): ");
        String inputIdentifier = input.nextLine();
        if (exit(inputIdentifier)) return;

        // Check for duplicate account
        for (int i = 0; i < accounts.size(); i++) {
            String checkUser = accounts.get(i).getUser();
            String checkIdentifier = accounts.get(i).getIdentifier();

            if (user.equals(checkUser)) {
                if (inputIdentifier.equalsIgnoreCase(checkIdentifier)) {
                    System.out.println("An account with the identifier " + inputIdentifier + " already exists. Would you like to edit the account into? (y/n) ");
                    String answer = input.next();
                    if (answer.equalsIgnoreCase("n")) {
                        System.out.println("Returning to main menu.\n");
                        return;
                    }

                    //Edit existing account
                    else {
                        System.out.println("Enter updated info:");
                        System.out.print("Username: ");
                        String inputUsername = input.next();
                        if (exit(inputUsername)) return;

                        System.out.print("Password: ");
                        String inputPassword = input.next();
                        if (exit(inputPassword)) return;

                        accounts.get(i).setUsername(inputUsername);
                        accounts.get(i).setPassword(inputPassword);
                        System.out.println("Account updated.");
                        return;
                    }
                }

            }

        }

        System.out.print("Username: ");
        String inputUsername = input.nextLine();
        if (exit(inputUsername)) return;

        System.out.print("Password: ");
        String inputPassword = input.nextLine();
        if (exit(inputPassword)) return;

        AccountsV2 newAccount = new AccountsV2(user, userPassword, inputIdentifier, inputUsername, inputPassword);
        accounts.add(newAccount);
        System.out.println("Account added.");
    }

    // Display all identifiers
    /**
     * This method finds and prints all the accounts added under the current user.  
     * 
	 * @param user (String; the user logged into the program)
	 * @param accounts (ArrayList<AccountsV2>; an arraylist of AccountsV2 objects)
     * @param identifierCheck (String; the identifier the program ensures is not a placeholder, if so, the method prints it)
	 * @param checkUser (String; the user saved in an AccountsV2 object that the program uses to only find the accounts that belong to the user.)
	 *       
	 */
    static void displayIdentifiers(String user, ArrayList<AccountsV2> accounts) {
        System.out.println("\nUser " + user + " saved accounts: ");
        int count = 0;

        // Find and print the account identifiers tied to the user
        for(int i = 0; i < accounts.size(); i++) {
            String userCheck = accounts.get(i).getUser();
            if (user.equals(userCheck)) {
                String identifierCheck = accounts.get(i).getIdentifier();
                if (identifierCheck.equals("placeholderIdentifier") == false) {
                    System.out.println(accounts.get(i).getIdentifier());
                    count++;
                }
            }
        }
        if (count == 0) {
            System.out.println("No accounts found for user " + user);
            System.out.println();
        }
    }

    // Display account info
    /**
     * This method gets a target account's identifier from the user and displays that accounts username and password. 
     * 
	 * @param input (Scanner; Scans the system to get inputs from the user)
	 * @param accounts (ArrayList<AccountsV2>; an arraylist of AccountsV2 objects)
	 * @param targetIdentifier (String; the account the user would like to display.)
	 * @param user (String; the user currently logged into the program)
	 * @param userCheck (String; the user saved in an AccountsV2 object that the program checks to see if it matches the user.)
	 * @param identifierCheck (String; the current identifier the program is comparing to targetIdentifier)
	 *       
	 * 
	 */
    static void displayAccount(String user, ArrayList<AccountsV2> accounts) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter account identifier to display account info: ");
        String targetIdentifier = input.next();

        for (int i = 0; i < accounts.size(); i++) {
            String userCheck = accounts.get(i).getUser();
            if (user.equals(userCheck)) {
                String identifierCheck = accounts.get(i).getIdentifier();
                if (targetIdentifier.equalsIgnoreCase(identifierCheck)) {
                    System.out.println(targetIdentifier + " account info:");
                    System.out.println("Username: " + accounts.get(i).getUsername());
                    System.out.println("Password: " + accounts.get(i).getPassword());
                    return;
                }
            }
        }
        System.out.println(targetIdentifier + " account not found. Returning to main menu.\n");

    }

    // Delete Account
    /**
     * This method deletes an account from the accounts arraylist. 
     * 
	 * @param input (Scanner; Scans the system to get inputs from the user)
	 * @param accounts (ArrayList<AccountsV2>; an arraylist of AccountsV2 objects)
	 * @param targetAccount (String; the account the user would like to delete.)
	 * @param user (String; the user currently logged into the program)
	 * @param userCheck (String; the user saved in an AccountsV2 object that the program checks to see if it matches the user.)
	 * @param identifierCheck (String; the current identifier the program is comparing to targetIdentifier)
	 *       
	 * 
	 */
    static void deleteAccount(String user, ArrayList<AccountsV2> accounts) {
        Scanner input = new Scanner(System.in);
        System.out.println("Delete Account");
        System.out.println("-----------------");
        System.out.println("Which account would you like to delete?");
        String targetAccount = input.next();

        //Check target account exists
        for (int i = 0; i < accounts.size(); i++) {
            String userCheck = accounts.get(i).getUser();
            if (user.equals(userCheck)) {
                String identifierCheck = accounts.get(i).getIdentifier();
                if(targetAccount.equalsIgnoreCase(identifierCheck)) {

                    //If account found, confirm user wants to remove the account
                    System.out.println("Are you sure you want to delete " + targetAccount + " account info? (y/n)");
                    String confirm = input.next();
                    if (confirm.equalsIgnoreCase("y")){
                        accounts.remove(i);
                        System.out.println("Account removed. Returning to main menu.\n");
                        return;
                    }
                    else {
                        System.out.println("No account removed. Returning to main menu.\n");
                        return;
                    }
                }
            }
        }

    }

    // Delete User
    /**
     * This method deletes all of one user's accounts, deleting the user in the process. 
     * 
	 * @param input (Scanner; Scans the system to get inputs from the user)
	 * @param accounts (ArrayList<AccountsV2>; an arraylist of AccountsV2 objects)
	 * @param confirm (String; the string the user inputs to confirm they want to remove the user and all their accounts.)
	 * @param user (String; the user currently logged into the program)
	 * @param count (int; counts the number of AccountsV2 tied to the user in accounts. The removal loop runs again if this number is greater than 0.)
	 * @param checkUser (String; the current user the program is comparing to user)
	 *       
	 * @return (Boolean; the program returns false if the account is deleted and true if the user backs out) 
	 */
    static boolean deleteUser(String user, ArrayList<AccountsV2> accounts) {
        Scanner input = new Scanner(System.in);
        System.out.println("Delete User");
        System.out.println("---------------");
        System.out.println("Are you sure you want to delete the current user and all the assosiated accounts? (y/n)");
        String confirm = input.next();
        if(confirm.equalsIgnoreCase("y")) {

            // Remove users until no accounts remain
            int count;
            do {
                count = 0;
                removeUser(user, accounts);
                for (int i = 0; i < accounts.size(); i++) {
                    String checkUser = accounts.get(i).getUser();
                    if (checkUser.equals(user)) {
                        count++;
                    }
                }
            } while (count > 0);

            System.out.println("User " + user + " deleted. Returning to welcome menu.\n");
            return false;
        }

        else {
            System.out.println("User deletion cancled. Returning to main menu.\n");
            return true;
        }


    }

    // Logout 
    /**
     * This method logs out the current user. 
     * It signals to the rest of the program to go back to the welcome menu or return to the main if the user backs out of the logout process.  
     * 
	 * @param input (Scanner; Scans the system to get inputs from the user)
	 * @param user (String; the user currently logged into the program)
	 * @param confirm (String; confirmation entered by the user to trigger the log out, or back out.)
	 *       
	 * @return (boolean; returns false if the user continues with the logout process and true if the user backs out of the log out process)
	 */
    static boolean logout(String user) {
        Scanner input = new Scanner(System.in);
        System.out.println("Are you sure you wish to logout user " + user + "? (y/n)");
        String confirm = input.next();
        if(confirm.equalsIgnoreCase("y")) {
            System.out.println("Logging out " + user + ". Returning to Welcome menu.\n");
            return false;
        }
        else {
            System.out.println("Returning to main menu.\n");
            return true;
        }
    }

    static void removeUser(String user, ArrayList<AccountsV2> accounts) {
        for(int i = 0; i < accounts.size(); i++) {
            String checkUser = accounts.get(i).getUser();
            if (checkUser.equals(user)) {
                accounts.remove(i);
            }
        }
    }

    // Write File Method (more encryption layers can be added here, if desired)
    /**
     * This method writes all the AccountsV2 objects info to the text file. It adds a layer of encryption beforehand. 
     * More encryption layers can be added here down the road. 
     * 
	 * @param output (PrintWriter; object used to write the info to the text file)
	 * @param accounts (ArrayList<AccountsV2>; an arraylist of AccountsV2 objects)
	 * @param file (File; the text file being written to)
	 * @param user (String; the user pulled from the AccountsV2 object in the array)
	 * @param userPassword (String; the userPassword pulled from the AccountsV2 object in the array)
	 * @param identifier (String; the identifier pulled from the AccountsV2 object in the array)
	 * @param username (String; the username pulled from the AccountsV2 object in the array)
	 * @param password (String; the password pulled from the AccountsV2 object in the array)
	 * @param key (int; the key generated using the user and userpassword so that every user has a unique key. Used to encrypt the text.)
	 * 
	 */
    static void writeFile(File file, ArrayList<AccountsV2> updates) throws FileNotFoundException{
        PrintWriter output = new PrintWriter("Accounts.txt");
        for (int i = 0; i < updates.size(); i++){
            // Encrypt AccountsV2 object info and write to the txt file.

            // Get account info from arraylist
            String user = updates.get(i).getUser();
            String userPassword = updates.get(i).getUserPassword();
            String identifier = updates.get(i).getIdentifier();
            String username = updates.get(i).getUsername();
            String password = updates.get(i).getPassword();

            // Encrypt account info        	
            int key = makeKey(user, userPassword);
            user = encryption(user, key);
            userPassword = encryption(userPassword, key);
            identifier = encryption(identifier, key);
            username = encryption(username, key);
            password = encryption(password, key);

            // Write account into to the txt file
            output.println(user + " " + userPassword + " " + identifier + " " + username + " " + password + " ");
        }
        output.close();
    }

    //Read File Method (more decryption layers can be added here, if desired)
    /**
     * This method pulls Strings from the text file, decrypts them, creates AccountsV2 objects and saves those objects to an arraylist.
     * More decryption layers can be added here down the road. 
     * 
	 * @param input (Scanner; object used to read strings from the file)
	 * @param file (File; the text file being read)
	 * @param user (String; the user pulled from the text file and saved to an AccountsV2 object in the array)
	 * @param userPassword (String; the userPassword pulled from the text file and saved to an AccountsV2 object in the array)
	 * @param identifier (String; the identifier pulled from the text file and saved to an AccountsV2 object in the array)
	 * @param username (String; the username pulled from the text file and saved to an AccountsV2 object in the array)
	 * @param password (String; the password pulled from the text file and saved to an AccountsV2 object in the array)
	 * @param key (int; the key generated using the user and userpassword so that every user has a unique key. Used to decrypt the text.)
	 * 
	 * @return accounts (ArrayList<AccountsV2>; an arraylist of AccountsV2 objects)
	 * 
	 * @throws FileNotFoundException
	 * 
	 */
    static ArrayList<AccountsV2> readFile(File file) throws FileNotFoundException{
        Scanner input = new Scanner(file);
        ArrayList<AccountsV2> accounts = new ArrayList<>();
        while (input.hasNext()){

            // Pull account info from txt file
            String user = input.next();
            String userPassword = input.next();
            String identifier = input.next();
            String username = input.next();
            String password = input.next();

            // Decrypt account info (Can add more decryption layers here)
            int key = makeKey(user, userPassword);
            user = decryption(user, key);
            userPassword = decryption(userPassword, key);
            identifier = decryption(identifier, key);
            username = decryption(username, key);
            password = decryption(password, key);

            // Create account objects and add to the arraylist
            accounts.add(new AccountsV2(user, userPassword, identifier, username, password));
        }

        return accounts;
    }

    // Encryption Method
    /**
     * This method takes in a string and a key and returns the text encrypted. 
     * 
     * <pre>Example:
     * {@code encryption(abc, 1) returns bcd
     * 
     * }</pre>
     * 
     * @param plainText (String; the text to be encrypted)
     * @param key (int; the key used to shift each char of the plaintext)
     * @param plainChars (char[]; plainText as an array of characters)
     * @param temporaryChar (char; the temporary character that is rotated)
     * 
     * @return encryptedText (String; the result of the rotated chars of the plaintext)
     */
    static String encryption(String plainText, int key) {
        char[] plainChars = plainText.toCharArray();
        String encryptedText = "";
        char temporaryChar = 0;
        for (int i = 0; i < plainText.length(); i++) {
            temporaryChar = (char) ((plainChars[i] + key));
            encryptedText = encryptedText + temporaryChar;
        }

        return encryptedText;
    }

    // Decryption method
    /**
     * This method takes in a string and a key and returns the text decrypted. 
     * 
     * <pre>Example:
     * {@code decryption(bcd, 1) returns abc
     * 
     * }</pre>
     * 
     * @param encryptedText (String; the result of the rotated chars of the plaintext)
     * @param key (int; the key used to shift each char of the encryptedText)
     * @param encryptedChars (char[]; encryptedText as an array of characters)
     * @param temporaryChar (char; the temporary character that is rotated)
     * 
     * @return plainText (String; the text decrypted)
     */
    static String decryption(String encryptedText, int key) {
        char[] encryptedChars = encryptedText.toCharArray();
        String plainText = "";
        char TemporaryChar = 0;
        for (int i = 0; i < encryptedText.length(); i++) {
            TemporaryChar = (char) ((encryptedChars[i] - key));
            plainText = plainText + TemporaryChar;
        }

        return plainText;
    }

    /**
     * This method takes in two strings and returns the key used in encryption and decryption.
     * The key is generated by adding the length of both strings. 
     * 
     * <pre> Examples: 
     * {@code	makeKey(user, pass) returns 8
     * 	makeKey(Beans, Cheese) returns 11
     * }</pre>
     * 
     * @param user (String; the user entered. The length is used to generate the key)
     * @param pass (String; the user password entered. The length is used to generate the key)
     * 
     * @return key (int; key generated by adding the length of the two strings together)
     */
    static int makeKey(String user, String pass) {
        int key = user.length() + pass.length();
        return key;
    }
}

