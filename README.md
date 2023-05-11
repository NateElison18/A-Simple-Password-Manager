# A-Simple-Password-Manager 

## Synopsis
This project served as the final project for the Object Oriented Programing 1 course at Southwest Tech (see OOP1 repository for the rest of the course work). As the title states, this is a simple password manager that users can utilize to save account information to an encrypted text file. The account information can be later be decrypted and displayed in plaintext to the user. The program can support multiple users. Users only have access to the accounts they add. 

## Motivation
I put the project together to demonstrate what I have learned in OOP1. It is also a tool which can be used to keep track of the many accounts and passwords we are required to rememeber. I, for one, am sick of forgetting and resetting my H&R Block password once a year come tax season. 

## How to Run
The program is pretty self explanitory. The first time run, it will prompt the user to create the first User, before requiring them to login. From there users are faced with the main menu with multiple options. 
It is important to remember spaces in account information will break the text file and the program. Also, apart from the first user, any subsequent users created must first create an account before logging out, or else the text file will not save their user login info (deleting the user). 

![First time user experience](https://github.com/NateElison18/A-Simple-Password-Manager/blob/main/First%20Time%20User%20Experience.png)

## Code Example
the runProgram method combines methods into try/catch blocks and loops so that the text file can be created and initialized to be used in the rest of the program. The code can be further improved by further separating the code into even more methods. 
```
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
```

## Tests
I have included a JUnit4 testing file, which successfully tests the value returning methods that do not require user input.

## Contributors
So far, the project has been a one man job. However, there are many improvements that could be made such as: 

+ Make it so the program doesn't end when an invalid format is input by the user when selecting from the menus. 
+ Not allowing the user to input spaces when entering account/user info
+ Disallow the user to select options to display account info when there are no accounts yet saved

If you would like to make any improvements, please reach out to me at elisonnathan@gmail.com. 
