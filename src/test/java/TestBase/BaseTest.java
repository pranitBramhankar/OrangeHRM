package TestBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;


public class BaseTest {
	public WebDriver driver;
	public Properties properties;
	public org.apache.logging.log4j.Logger logger;
	
	@BeforeMethod
	@Parameters ({"browser"})
	public void launchBrowser(String browser, ITestContext context) {
		
		switch(browser) {
			case "chrome" : this.driver = new ChromeDriver(); break;
			case "edge" : this.driver = new EdgeDriver(); break;
			case "firefox" : this.driver = new FirefoxDriver(); break;
			default : throw new IllegalArgumentException(browser);
		}
		
		context.setAttribute("WebDriver", this.driver);
		
		//set implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//Set Properties file
		try {
			FileReader file = new FileReader("C:\\Users\\prani\\git\\OrangeHRM\\src\\test\\resources\\commonValues.properties");
			properties = new Properties();
			properties.load(file);
		} catch(Exception e) {
			e.getStackTrace();
		} 
		
		driver.manage().window().maximize();
		
		//launch Application
		driver.get(properties.getProperty("ApplicationURL"));
		
		//log
		 
		   System.setProperty("className", this.getClass().getSimpleName());
	       logger = LogManager.getLogger(this.getClass());
	       logger.info("Logging initialized for " + this.getClass().getSimpleName());

	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public String captureScreen(String tname) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile=new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
			
		return targetFilePath;

	}	
	
	@AfterMethod
	public void tearDownAllSessions() {
		driver.quit();
	}

}
