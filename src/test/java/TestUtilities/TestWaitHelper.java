package TestUtilities;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestWaitHelper {
	
	private WebDriver driver;
	
	public TestWaitHelper(WebDriver driver) {
		this.driver=driver;
		
		System.out.println(this.driver.hashCode());
	}
	
	public WebElement visibilityOfElement(By locator) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
	    WebElement elementPresent = wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
	    return elementPresent;
	}

}
