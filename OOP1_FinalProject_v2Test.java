import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import org.junit.jupiter.api.Test;

class OOP1_FinalProject_v2Test {

	@Test
	void testExit() {
		assertTrue(OOP1_FinalProject_v2.exit("exit"));
		assertTrue(OOP1_FinalProject_v2.exit("ExIt"));
		assertFalse(OOP1_FinalProject_v2.exit("not Exit"));
	}
	
	@Test
	void testReadFile() throws FileNotFoundException {
		File file = new File("test.txt");
		PrintWriter output = new PrintWriter(file);
		String testUser = "testuser";
		String testPass = "testpass";
		String testId = "testid";
		String testUsername = "testusername";
		String testPassword = "testpassword";
		
		int key = OOP1_FinalProject_v2.makeKey(testUser, testPass);
		AccountsV2 testAccount = new AccountsV2(testUser, testPass, testId, testUsername, testPassword);
		ArrayList<AccountsV2> testArray = new ArrayList<>();
		testArray.add(testAccount);
		
		testUser = OOP1_FinalProject_v2.encryption(testUser, key);
		testPass = OOP1_FinalProject_v2.encryption(testPass, key);
		testId = OOP1_FinalProject_v2.encryption(testId, key);
		testUsername = OOP1_FinalProject_v2.encryption(testUsername, key);
		testPassword = OOP1_FinalProject_v2.encryption(testPassword, key);
		
		output.print(testUser + " " + testPass + " " + testId + " " + testUsername + " " + testPassword);
		output.close();
		
		ArrayList<AccountsV2> testArray2 = OOP1_FinalProject_v2.readFile(file);
		
		assertEquals(testArray2.get(0).user, testArray.get(0).user);
		assertEquals(testArray2.get(0).userPassword, testArray.get(0).userPassword);
		assertEquals(testArray2.get(0).identifier, testArray.get(0).identifier);
		assertEquals(testArray2.get(0).username, testArray.get(0).username);
		assertEquals(testArray2.get(0).password, testArray.get(0).password);	
	}
	
	@Test
	void testEncryption() {
		assertEquals(OOP1_FinalProject_v2.encryption("abcde", 1), "bcdef");
	}
	
	@Test
	void testDecryption() {
		assertEquals(OOP1_FinalProject_v2.decryption("bcdef", 1), "abcde");
	}
	
	@Test
	void testMakeKey() {
		assertEquals(OOP1_FinalProject_v2.makeKey("user", "pass"), 8);
		assertEquals(OOP1_FinalProject_v2.makeKey("Test", "Test2"), 9); 
				
	}
}
