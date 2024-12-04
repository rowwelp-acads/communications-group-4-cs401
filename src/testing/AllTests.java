package testing;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
	UserAccount_Testing.class,
	ConvoLogTest.class,
	chatTest.class,
	ChatList_Testing.class,
	ChatListManager_Testing.class,
	ServerTest.class
})

public class AllTests {
}