package Utilities;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

/**
 * Centralized element actions that use WaitHelper and RetryUtil to provide
 * robust operations for page objects. Retries transient exceptions like
 * StaleElementReferenceException and ElementClickInterceptedException.
 */
public class ActionHelper {
    private static final Logger LOGGER = Logger.getLogger(ActionHelper.class.getName());

    private final WebDriver driver;
    private final WaitHelper waitHelper;

    // sensible defaults; callers can implement overloads if different values are needed
    private static final int DEFAULT_MAX_ATTEMPTS = 3;
    private static final long DEFAULT_DELAY_MS = 200;

    public ActionHelper(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
    }

    public void click(By locator) {
        try {
            RetryUtil.retryOnException(() -> {
                WebElement el = waitHelper.waitForClickable(locator);
                el.click();
            }, DEFAULT_MAX_ATTEMPTS, DEFAULT_DELAY_MS,
                    StaleElementReferenceException.class, ElementClickInterceptedException.class,
                    NoSuchElementException.class);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Click failed for locator: " + locator + " - " + e.getMessage(), e);
            throw new WrapperException("Failed to click element: " + locator, e);
        }
    }

    public void type(By locator, String text) {
        try {
            RetryUtil.retryOnException(() -> {
                WebElement el = waitHelper.waitForVisibility(locator);
                el.clear();
                el.sendKeys(text);
            }, DEFAULT_MAX_ATTEMPTS, DEFAULT_DELAY_MS,
                    StaleElementReferenceException.class, NoSuchElementException.class);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Type failed for locator: " + locator + " - " + e.getMessage(), e);
            throw new WrapperException("Failed to type into element: " + locator, e);
        }
    }

    public String getText(By locator) {
        try {
            return RetryUtil.retryOnException(() -> {
                WebElement el = waitHelper.waitForVisibility(locator);
                return el.getText();
            }, DEFAULT_MAX_ATTEMPTS, DEFAULT_DELAY_MS, StaleElementReferenceException.class,
                    NoSuchElementException.class);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "getText failed for locator: " + locator + " - " + e.getMessage(), e);
            throw new WrapperException("Failed to get text from element: " + locator, e);
        }
    }

    public boolean isDisplayed(By locator) {
        try {
            return RetryUtil.retryOnException(() -> {
                WebElement el = waitHelper.waitForVisibility(locator);
                return el.isDisplayed();
            }, DEFAULT_MAX_ATTEMPTS, DEFAULT_DELAY_MS, StaleElementReferenceException.class,
                    NoSuchElementException.class);
        } catch (RuntimeException e) {
            LOGGER.log(Level.FINE, "isDisplayed failed for locator: " + locator + " - " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Generic operation with retry. Allows page objects to pass in arbitrary
     * lambdas that operate on the driver or elements.
     */
    @SafeVarargs
    public final <T> T performWithRetry(java.util.function.Supplier<T> operation, int maxAttempts, long delayMillis,
            Class<? extends Throwable>... retryOn) {
        try {
            return RetryUtil.retryOnException(operation, maxAttempts, delayMillis, retryOn);
        } catch (RuntimeException e) {
            throw e; // let caller handle
        }
    }
}
