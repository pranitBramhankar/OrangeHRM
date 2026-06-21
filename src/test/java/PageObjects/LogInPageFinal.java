package PageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import TestUtilities.TestActionHelper;

public class LogInPageFinal {
	
	//objects
	private WebDriver driver;
	private	TestActionHelper actionHelper; 
	
	private static final Logger logger =  LogManager.getLogger(LogInPageFinal.class);
	
	//constructors
	public LogInPageFinal(WebDriver driver) {
		this.driver = driver;
		this.actionHelper = new TestActionHelper(driver);
	}
	
	//WebElements
	private By usernameTextboxLocator = By.xpath("//input[@name='username']");
	private By passwordTextboxLocator = By.cssSelector("input[type='password']");
	private By logInBtn = By.xpath("//button[text()=' Login ']");
	private By InvalidUsernameMessageLocator = By.xpath("//p[text()='Invalid credentials']");
	
	//Actions
	
	public void enterUsername(String username) {
		actionHelper.typeIntoTextBox(usernameTextboxLocator, username, "Username Textbox");
	}
	
	public void enterPassword(String password) {
		actionHelper.typeIntoTextBox(passwordTextboxLocator, password, "Password Textbox");
	}
	
	public void clickLogInButton() {
		actionHelper.click(logInBtn,"Log In Button");		
	}
	
	public String getInvalidUsernameMessage() {
		return actionHelper.getMessageTest(InvalidUsernameMessageLocator, "Invalid Credential Message");
	}
}
