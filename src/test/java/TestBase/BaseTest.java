package TestBase;

import java.io.FileReader;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;


public class BaseTest {
	public WebDriver driver;
	public Properties properties;
	
	
	@BeforeMethod
	@Parameters ({"browser"})
	public void launchBrowser(String browser) {
		
		switch(browser) {
			case "chrome" : driver = new ChromeDriver(); break;
			case "edge" : driver = new EdgeDriver(); break;
			case "firefox" : driver = new FirefoxDriver(); break;
			default : throw new IllegalArgumentException(browser);
		}
		
		//set implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//Set Properties file
		try {
			FileReader file = new FileReader("C:\\Users\\prani\\eclipse-workspace\\OrangeHRM\\src\\test\\resources\\commonValues.properties");
			properties = new Properties();
			properties.load(file);
		} catch(Exception e) {
			e.getStackTrace();
		} 
		
		driver.manage().window().maximize();
		
		//launch Application
		driver.get(properties.getProperty("ApplicationURL"));

	}
	
	@AfterMethod
	public void tearDownAllSessions() {
		driver.quit();
	}

}
