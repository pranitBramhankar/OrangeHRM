package TestUtilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Utilities.ActionHelper;
import Utilities.WaitHelper;

public class TestLogInPage {
	
	
	private final WebDriver driver;
    private final TestActionHelper actionHelper;

    // Locators - update these if your application uses different attributes
    private final By usernameField = By.xpath("//input[@name='username']");
    private final By passwordField = By.cssSelector("input[type='password']");
    private final By loginButton = By.xpath("//button[text()=' Login ']");
    private final By invalidCredentialsMessage = By.id("spanMessage");
    // Generic "required" messages - tune these xpaths if your app renders them differently
    private final By usernameRequiredMessage = By.xpath("//span[contains(text(),'Required') and (preceding::input[@id='txtUsername'] or following::input[@id='txtUsername'])]");
    private final By passwordRequiredMessage = By.xpath("//span[contains(text(),'Required') and (preceding::input[@id='txtPassword'] or following::input[@id='txtPassword'])]");
    
    public TestLogInPage(WebDriver driver) {
        this.driver = driver;
        this.actionHelper = new TestActionHelper(this.driver);
        
        System.out.println(this.driver.hashCode());
    }
    
    public void enterUserName(String username) {
        actionHelper.typeIntoTextBox(usernameField, username);
    }

    public void enterPassword(String password) {
        actionHelper.typeIntoTextBox(passwordField, password);
    }

    public void clickLoginButton() {
        actionHelper.click(loginButton);
    }
    
    
}
