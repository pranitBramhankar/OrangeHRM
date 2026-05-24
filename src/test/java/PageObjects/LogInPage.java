package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogInPage {
	private WebDriver driver;
	
	public LogInPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver,this);
	}
	
	@FindBy(xpath="//input[@name='username']")
	WebElement username;
	
	@FindBy(css="input[type='password']")
	WebElement password;
	
	@FindBy(xpath="//button[text()=' Login ']")
	WebElement loginBtn;
	
	@FindBy(xpath="")
	WebElement invalidCredentialMessage;
	
	@FindBy(xpath="")
	WebElement usernameRequiredMessage;
	
	@FindBy(xpath="")
	WebElement passwordRequiredMessage;
	
	public void enterUserName(String userName) {
		username.sendKeys(userName);
	}
	
	public void enterPassword(String passWord) {
		password.sendKeys(passWord);
	}
	
	public void clickLogInButton() {
		loginBtn.click();
	}
	

}
