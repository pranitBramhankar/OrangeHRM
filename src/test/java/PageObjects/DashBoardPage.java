package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashBoardPage {
	
	private WebDriver driver;
	
	public DashBoardPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver,this);
	}
	
	@FindBy(xpath="//h6[text()='Dashboard']")
	WebElement dashBoardHeading;
	
	public boolean verifyDashboardVisible() {
		return dashBoardHeading.isDisplayed();
	}
}
