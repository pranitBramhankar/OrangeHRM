 package TestUtilities;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestCase {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		TestLogInPage lp = new TestLogInPage(driver);
		lp.enterUserName("Admin");
		lp.enterPassword("admin123");
		lp.clickLoginButton();
		
		
		

	}

}
