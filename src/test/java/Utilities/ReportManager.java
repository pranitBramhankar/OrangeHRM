package Utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import TestBase.BaseTest;

public class ReportManager implements ITestListener {

	private static final Logger logger = LogManager.getLogger(ReportManager.class);

	private static ExtentReports extent;
	private static ExtentTest test;

	private String reportName;

	@Override
	public void onStart(ITestContext context) {

		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

		reportName = "ExtentReport_" + timestamp + ".html";

		String reportPath = System.getProperty("user.dir") + File.separator + "Reports" + File.separator + reportName;

		ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

		spark.config().setDocumentTitle("OrangeHRM Automation Execution Report");

		spark.config().setReportName("Selenium Test Execution");

		spark.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();

		extent.attachReporter(spark);

		extent.setSystemInfo("Tester", System.getProperty("user.name"));

		extent.setSystemInfo("Framework", "Selenium + TestNG");

		extent.setSystemInfo("Environment", "QA");

		logger.info("====================================================");
		logger.info("Extent Report Initialized : {}", reportName);
		logger.info("====================================================");
	}

	@Override
	public void onTestStart(ITestResult result) {

		test = extent.createTest(result.getMethod().getMethodName());

		test.info("Execution Started");

		logger.info("");
		logger.info("====================================================");
		logger.info("START TEST : {}", result.getMethod().getMethodName());
		logger.info("====================================================");
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		test.pass("Test Passed");

		logger.info("TEST PASSED : {}", result.getMethod().getMethodName());

		try {

			BaseTest base = (BaseTest) result.getInstance();

			String screenshotPath = base.captureScreen(result.getMethod().getMethodName());

			test.addScreenCaptureFromPath(screenshotPath);

			logger.info("Screenshot attached : {}", screenshotPath);

		} catch (Exception e) {

			test.warning("Unable to attach screenshot : " + e.getMessage());

			logger.warn("Unable to attach screenshot : {}", e.getMessage());
		}

		logger.info("====================================================");
	}

	@Override
	public void onTestFailure(ITestResult result) {

		test.fail(result.getThrowable());

		logger.error("TEST FAILED : {}", result.getMethod().getMethodName());
		logger.error("Failure Reason : ", result.getThrowable());

		try {

			BaseTest base = (BaseTest) result.getInstance();

			String screenshotPath = base.captureScreen(result.getMethod().getMethodName());

			test.addScreenCaptureFromPath(screenshotPath);

			logger.info("Screenshot attached : {}", screenshotPath);

		} catch (Exception e) {

			test.warning("Unable to attach screenshot : " + e.getMessage());

			logger.warn("Unable to attach screenshot : {}", e.getMessage());
		}

		logger.info("====================================================");
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		test.skip("Test Skipped");

		if (result.getThrowable() != null) {

			test.skip(result.getThrowable());
		}

		logger.warn("TEST SKIPPED : {}", result.getMethod().getMethodName());

		if (result.getThrowable() != null) {

			logger.warn("Skip Reason : ", result.getThrowable());
		}

		logger.info("====================================================");
	}

	@Override
	public void onFinish(ITestContext context) {

		extent.flush();

		logger.info("Extent Report Generated Successfully");
		logger.info("Report Name : {}", reportName);

		String reportPath = System.getProperty("user.dir") + File.separator + "Reports" + File.separator + reportName;

		try {

			File report = new File(reportPath);

			if (report.exists()) {

				Desktop.getDesktop().browse(report.toURI());

				logger.info("Report opened successfully.");
			}

		} catch (IOException e) {

			logger.error("Unable to open report", e);
		}
	}

	public static void info(String message) {

		if (test != null) {

			test.info(message);
		}
	}

	public static void pass(String message) {

		if (test != null) {

			test.pass(message);
		}
	}

	public static void fail(String message) {

		if (test != null) {

			test.fail(message);
		}
	}

	public static ExtentTest getTest() {

		return test;
	}
}

