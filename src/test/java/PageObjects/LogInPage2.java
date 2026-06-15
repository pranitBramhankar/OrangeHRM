package PageObjects;

import Utilities.ActionHelper;
import Utilities.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Reference Page Object for the Login page.
 *
 * - Uses By locators (no PageFactory)
 * - Uses ActionHelper and WaitHelper for interactions and waits
 * - Kept as a separate file for future reference per request
 */
public class LogInPage2 {
    private final WebDriver driver;
    private final ActionHelper actionHelper;
    private final WaitHelper waitHelper;

    // Locators - update these if your application uses different attributes
    private final By usernameField = By.id("txtUsername");
    private final By passwordField = By.id("txtPassword");
    private final By loginButton = By.id("btnLogin");
    private final By invalidCredentialsMessage = By.id("spanMessage");
    // Generic "required" messages - tune these xpaths if your app renders them differently
    private final By usernameRequiredMessage = By.xpath("//span[contains(text(),'Required') and (preceding::input[@id='txtUsername'] or following::input[@id='txtUsername'])]");
    private final By passwordRequiredMessage = By.xpath("//span[contains(text(),'Required') and (preceding::input[@id='txtPassword'] or following::input[@id='txtPassword'])]");

    public LogInPage2(WebDriver driver) {
        this.driver = driver;
        this.actionHelper = new ActionHelper(driver);
        this.waitHelper = new WaitHelper(driver);

        // Wait for the username field to be visible as a sign the page has loaded
        waitHelper.waitForVisibility(usernameField);
    }

    public void enterUserName(String username) {
        actionHelper.type(usernameField, username);
    }

    public void enterPassword(String password) {
        actionHelper.type(passwordField, password);
    }

    public void clickLoginButton() {
        actionHelper.click(loginButton);
    }

    public void loginAs(String username, String password) {
        enterUserName(username);
        enterPassword(password);
        clickLoginButton();
    }

    public String getInvalidCredentialMessage() {
        return actionHelper.getText(invalidCredentialsMessage);
    }

    public boolean isUsernameRequiredMessageDisplayed() {
        return actionHelper.isDisplayed(usernameRequiredMessage);
    }

    public boolean isPasswordRequiredMessageDisplayed() {
        return actionHelper.isDisplayed(passwordRequiredMessage);
    }

    // Expose locator getters in case tests need to assert presence or use them directly
    public By getUsernameFieldLocator() { return usernameField; }
    public By getPasswordFieldLocator() { return passwordField; }
    public By getLoginButtonLocator() { return loginButton; }
}
