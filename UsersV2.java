// User class
/**
 * The class allows for the creation of UsersV2 objects. They store two Strings: username and password. 
 * Getters and setters are included as well as two constructors: a default and one that takes in two strings for username and password, respectively.
 * Date created: 5/10/23
 * 
 * @author student
 * 
 */
class UsersV2{
    //Data fields
    String username = "%!%";
    String password = "%!%";

    // Constructors
    UsersV2(){
    }

    UsersV2(String newUsername, String newPassword){
        username = newUsername;
        password = newPassword;
    }

    // Getters
    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    // Setters
    void setUsername(String newUsername) {
        username = newUsername;
    }

    void setPassword(String newPassword) {
        password = newPassword;
    }
}

