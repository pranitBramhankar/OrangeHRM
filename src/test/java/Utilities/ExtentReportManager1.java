package Utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import TestBase.BaseTest;

public class ExtentReportManager1 implements ITestListener {

	private static final Logger logger = LogManager.getLogger(ExtentReportManager1.class);

	private ExtentSparkReporter sparkReporter;
	private ExtentReports extent;

	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	private String repName;

	public WebDriver driver;

	@Override
	public void onStart(ITestContext testContext) {

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

		repName = "Test-Report-" + timeStamp + ".html";

		sparkReporter = new ExtentSparkReporter("./reports/" + repName);

		sparkReporter.config().setDocumentTitle("OrangeHRM Automation Report");

		sparkReporter.config().setReportName("OrangeHRM Functional Testing");

		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();

		extent.attachReporter(sparkReporter);

		extent.setSystemInfo("Application", "OrangeHRM");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("User", System.getProperty("user.name"));

		String os = testContext.getCurrentXmlTest().getParameter("os");

		if (os != null) {
			extent.setSystemInfo("Operating System", os);
		}

		String browser = testContext.getCurrentXmlTest().getParameter("browser");

		if (browser != null) {
			extent.setSystemInfo("Browser", browser);
		}

		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();

		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}

		logger.info("Extent Report Initialized : {}", repName);
	}

	@Override
	public void onTestStart(ITestResult result) {

		ExtentTest test = extent.createTest(result.getMethod().getMethodName());

		test.assignCategory(result.getMethod().getGroups());

		extentTest.set(test);

		logger.info("\n==================== START TEST : {} ====================", result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		extentTest.get().log(Status.PASS, result.getMethod().getMethodName() + " executed successfully");

		logger.info("TEST PASSED : {}", result.getMethod().getMethodName());

		logger.info("==========================================================\n");
	}

	@Override
	public void onTestFailure(ITestResult result) {

		WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver");

		extentTest.get().log(Status.FAIL, result.getMethod().getMethodName() + " failed");

		extentTest.get().log(Status.INFO, result.getThrowable());

		try {

			String imgPath = new BaseTest().captureScreen(result.getMethod().getMethodName(), driver);

			extentTest.get().addScreenCaptureFromPath(imgPath);

		} catch (IOException e) {

			logger.error("Failed to attach screenshot", e);
		}

		logger.error("TEST FAILED : {}", result.getMethod().getMethodName(), result.getThrowable());

		logger.info("==========================================================\n");
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		extentTest.get().log(Status.SKIP, result.getMethod().getMethodName() + " skipped");

		logger.warn("TEST SKIPPED : {}", result.getMethod().getMethodName());

		logger.info("==========================================================\n");
	}

	@Override
	public void onFinish(ITestContext testContext) {

		extent.flush();

		logger.info("Extent Report Generated Successfully");

		String reportPath = System.getProperty("user.dir") + "/reports/" + repName;

		File reportFile = new File(reportPath);

		try {

			Desktop.getDesktop().browse(reportFile.toURI());

		} catch (IOException e) {

			logger.error("Unable to open extent report", e);
		}
	}
}
