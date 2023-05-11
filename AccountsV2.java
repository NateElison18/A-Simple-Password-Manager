/**
 * The class allows for the creation of AccountsV2 objects. They store five Strings: username, password, identifier, user, and userPassword. 
 * Getters and setters are included as well as two constructors: a default and one that takes in five strings for user, userPassword, identifier, username, and password.
 * Date created: 5/10/23
 * 
 * @author student
 * 
 */

class AccountsV2{

    // Data fields
    String username;
    String password;
    String identifier;
    String user;
    String userPassword;

    // Constructors
    AccountsV2(){

    }

    AccountsV2(String newUser, String newUserPassword, String newIdentifier, String newUsername, String newPassword) {
        username = newUsername;
        password = newPassword;
        identifier = newIdentifier;
        user = newUser;
        userPassword = newUserPassword;

    }

    // Getters
    String getUsername() {
        return username;
    }

    String getUser() {
        return user;
    }

    String getUserPassword() {
        return userPassword;
    }

    String getPassword() {
        return password;
    }

    String getIdentifier() {
        return identifier;
    }

    // Setters
    void setUsername(String newUsername) {
        username = newUsername;
    }

    void setuser(String newUser) {
        user = newUser;
    }

    void setPassword(String newPassword) {
        password = newPassword;
    }

    void setIdentifier(String newIdentifier) {
        identifier = newIdentifier;
    }
}