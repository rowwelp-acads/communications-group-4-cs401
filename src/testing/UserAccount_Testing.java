package testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.runners.Suite;
import main.java.UserAccount;
import main.java.RegularUser;
import org.junit.Test;

public class UserAccount_Testing {
	
	@Test
	public void constructorTest() {		
		String regName = "Bryan";
		String regPassword = "pass123";
		String regID = "1";
		
		UserAccount testReg = new RegularUser(regName,regPassword,regID,true);
		
		assertTrue( testReg.getUsername().equals(regName));
		assertTrue(testReg.getPassword().equals(regPassword));
		assertTrue( testReg.getID().equals(regID));
	}
	
	@Test
	public void setGetIDTest() {
		String regName = "Bryan";
		String regPassword = "pass123";
		String regID = "1";
		
		String testID = "99";
		
		UserAccount testReg = new RegularUser(regName,regPassword,regID,true);
		testReg.setUserId(testID);
		
		assertTrue( testReg.getID().equals(testID));
	}
	
	@Test
	public void setGetNameTest() {
		String regName = "Bryan";
		String regPassword = "pass123";
		String regID = "1";
		
		String testName = "Ryan";
		
		UserAccount testReg = new RegularUser(regName,regPassword,regID,true);
		testReg.setUsername(testName);
		
		assertTrue( testReg.getUsername().equals(testName));
	}
	
	
	@Test
	public void setGetPasswordTest() {
		String regName = "Bryan";
		String regPassword = "pass123";
		String regID = "1";
		
		String testPassword = "TheyWillNeverGuessThis";
		
		UserAccount testReg = new RegularUser(regName,regPassword,regID,true);
		testReg.setPassword(testPassword);
		
		assertTrue( testReg.getPassword().equals(testPassword));
	}
	
	@Test
	public void getCount() {
		String regName = "Bryan";
		String regPassword = "pass123";
		String regID = "1";
		int count = Integer.parseInt(regID);
		
		String testPassword = "TheyWillNeverGuessThis";
		
		UserAccount testReg = new RegularUser(regName,regPassword,regID,true);
		testReg.setCount(count);
		
		assertTrue( testReg.getID().equals(count));
	}

}
