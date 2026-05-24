package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.DashBoardPage;
import PageObjects.LogInPage;
import TestBase.BaseTest;

public class Test_LogInFunction extends BaseTest {
	
	@Test(priority = 1)
	public void validLogIn() {
		//enter Username
		LogInPage login = new LogInPage(driver);
		DashBoardPage dbp = new DashBoardPage(driver);
		
		login.enterUserName(properties.getProperty("Username"));
		login.enterPassword(properties.getProperty("Password"));
		login.clickLogInButton();
		Assert.assertEquals(dbp.verifyDashboardVisible(), true);
	}
	
	@Test(priority=2)
	public void InvalidUsername() {
		//enter Username
		LogInPage login = new LogInPage(driver);
		DashBoardPage dbp = new DashBoardPage(driver);
		login.enterUserName(properties.getProperty("Username"));
		login.enterPassword(properties.getProperty("Password"));
	}
}
