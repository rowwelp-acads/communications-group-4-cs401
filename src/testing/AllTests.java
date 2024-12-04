package testing;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
	UserAccount_Testing.class,
	ConvoLogTest.class,
	chatTest.class
})

public class AllTests {
}