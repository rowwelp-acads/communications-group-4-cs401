package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	UserAccount_Testing.class,
	ConvoLogTest.class
})

public class AllTests {
}