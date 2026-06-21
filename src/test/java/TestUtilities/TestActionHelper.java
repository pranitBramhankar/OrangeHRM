package TestUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Utilities.ReportManager;

public class TestActionHelper {

	private WebDriver driver;

	private TestWaitHelper twh;

	private static final Logger logger = LogManager.getLogger(TestActionHelper.class);

	public TestActionHelper(WebDriver driver) {
		this.driver = driver;
		System.out.println(this.driver.hashCode());
		// initialize the wait helper after driver is assigned to avoid NPE
		this.twh = new TestWaitHelper(this.driver);
	}

	public void click(By Locator, String elementName) {
		try {
			TestRetryMechanism.retryOnException(() -> {
				WebElement button = twh.visibilityOfElement(Locator);
				if (button != null) {
					logger.info("Element found for clicking: " + Locator);
				} else {
					logger.warn("Element not found for clicking: " + Locator);
				}
				button.click();
				logger.info("Successfully clicked element: " + Locator);

				ReportManager.info("Clicked : " + elementName);
			}, 2, StaleElementReferenceException.class, ElementClickInterceptedException.class,
					NoSuchElementException.class);

		} catch (Throwable e) {
			logger.error("Failed to click element: {}", Locator, e);
			throw new RuntimeException("Action Helper - Failed to click element: " + Locator, e);
		}

	}

	public void typeIntoTextBox(By Locator, String text, String elementName) {

		try {
			logger.info("Attempting to enter text into element: {} with text: {}", Locator, text);
			TestRetryMechanism.retryOnException(() -> {
				WebElement textBox = twh.visibilityOfElement(Locator);
				if (textBox != null) {
					logger.info("Element found for typing: " + Locator);
				} else {
					logger.warn("Element not found for typing: " + Locator);
				}
				textBox.sendKeys(text);
				logger.info("Successfully entered text into element: " + Locator);
				
				ReportManager.info(text + " entered into : " + elementName);
				
				return null;
			}, 2, StaleElementReferenceException.class, ElementClickInterceptedException.class,
					NoSuchElementException.class);
		} catch (Throwable e) {
			logger.error("Failed to type text into textbox: {}", Locator, e);
			throw new RuntimeException("Action Helper - Failed to enter text: " + Locator, e);
		}
	}

	public String getMessageTest(By Locator,String elementName) {
		try {
			logger.info("Attempting to get text from element: {}", Locator);
			return TestRetryMechanism.retryOnException(() -> {
				WebElement messageLocator = twh.visibilityOfElement(Locator);
				if (messageLocator != null) {
					logger.info("Element is visible to get messagge: {}", Locator);
				} else {
					logger.warn("Element is not visible to get message: {}", Locator);
				}
				String text = messageLocator.getText();
				if (text != null) {
					logger.info("Successfully get text from element: " + Locator);
				}
				ReportManager.info("Retrieved text: '" + text + "' from element: " + elementName);
				return text;
			}, 2, StaleElementReferenceException.class, ElementClickInterceptedException.class,
					NoSuchElementException.class);

		} catch (Throwable e) {
			logger.error("Failed to retrieve text from element: {}", Locator, e);
			throw new RuntimeException("Action Helper - Failed to get text: " + Locator, e);
		}
	}

}
