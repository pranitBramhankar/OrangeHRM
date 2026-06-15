package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;



import PageObjects.LogInPageFinal;
import TestBase.BaseTest;
import TestUtilities.JsonReader;

public class VerifyLogInInvalid extends BaseTest {
	
	@Test
	public void verifyInvalidPassword() {
		
		LogInPageFinal lp = new LogInPageFinal(driver);
		lp.enterUsername("A");
		lp.enterPassword("Admin");
		lp.clickLogInButton();
		Assert.assertEquals(JsonReader.getValue("VerifyLogInInvalid.invalidCredentialMessage"), lp.getInvalidUsernameMessage());
	}
	

}
